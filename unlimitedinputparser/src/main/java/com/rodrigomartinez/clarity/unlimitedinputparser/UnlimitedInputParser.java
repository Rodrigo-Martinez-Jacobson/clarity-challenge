package com.rodrigomartinez.clarity.unlimitedinputparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static com.rodrigomartinez.clarity.unlimitedinputparser.LogParser.connectedFrom;
import static com.rodrigomartinez.clarity.unlimitedinputparser.LogParser.connectedTo;
import static com.rodrigomartinez.clarity.unlimitedinputparser.LogParser.parse;


public class UnlimitedInputParser {
    public static void main(String... args) throws InterruptedException, IOException {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        final var is = args.length > 1 ? new FileInputStream(args[1]) : System.in;
        final var reader = new BufferedReader(new InputStreamReader(is));
        final var toHost = connectedTo(args[0]);
        final var fromHost = connectedFrom(args[0]);

        final var output =  new OutputAccumulator();

        final var ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(report(output), 1, 1, TimeUnit.HOURS);

        Thread printingHook = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Couldn't print report before the shutdown: "+e.getMessage());
            }
        });
        Runtime.getRuntime().addShutdownHook(printingHook);

        System.out.println("Starting Unlimited Input parser");
        reader
            .lines()
            .map(parse)
            .forEach(line -> processLogLine(toHost, fromHost, output, line));
        shutdown(output, ses);
    }

    private static void processLogLine(Predicate<LogLine> toHost, Predicate<LogLine> fromHost, OutputAccumulator output, LogLine line) {
        if (toHost.test(line)) output.addConnectedToHost(line.sourceHost());
        else if (fromHost.test(line)) output.addConnectedFromHost(line.targetHost());
        output.addConnectionByHost(line.sourceHost());
    }

    private static void shutdown(OutputAccumulator output, ScheduledExecutorService ses) throws InterruptedException {
        ses.execute(report(output));
        ses.awaitTermination(100, TimeUnit.MILLISECONDS);
        System.exit(0);
    }

    private static Runnable report(OutputAccumulator output) {
        return () -> {
            try {
                System.out.println("================================");
                System.out.println("List of hosts connected to given host:");
                System.out.println(output.connectedToHost.toString());
                System.out.println("List of hosts that received a connection from given host:");
                System.out.println(output.connectedFromHost.toString());
                System.out.println("Host with max connections: ");
                System.out.println(output.connectionsByHost.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(new AbstractMap.SimpleEntry<>("None", 0)));
                output.reset();
            } catch (Throwable t) {
                System.out.println("Error while generating the report " + t.getMessage());
            }
        };
    }

    private static void printUsage() {
        System.out.println("Unlimited input parser");
        System.out.println("Usage to parse a previously written file:");
        System.out.println("\tjava UnlimitedInputParser.jar <hostname> [filename]");
        System.out.println("Usage to parse a file while it's being written:");
        System.out.println("\ttail -f <filename> | java -jar unlimitedinputparser-1.0.jar <hostname>");
    }

    private static class OutputAccumulator {
        private Set<String> connectedToHost;
        private Set<String> connectedFromHost;
        private Map<String, Integer> connectionsByHost;

        public OutputAccumulator() {
            this.connectedToHost = ConcurrentHashMap.newKeySet();
            this.connectedFromHost = ConcurrentHashMap.newKeySet();
            this.connectionsByHost = new ConcurrentHashMap<>();
        }

        public void addConnectedToHost(final String host) {
            this.connectedToHost.add(host);
        }

        public void addConnectedFromHost(final String host) {
            this.connectedFromHost.add(host);
        }

        public void addConnectionByHost(String host) {
            this.connectionsByHost.merge(host, 1, Integer::sum);
        }

        public void reset() {
            this.connectedToHost.clear();
            this.connectedFromHost.clear();
            this.connectionsByHost.clear();
        }
    }
}
