package com.rodrigomartinez.clarity.listhosts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class LogParser {

    public static Supplier<Stream<String>> fromFile(final File file) {
        return () -> {
            try {
                return Files.lines(file.toPath());
            } catch (final IOException e) {
                e.printStackTrace(System.err);
                return Stream.empty();
            }
        };
   }

    static Function<String, LogLine> parse = line -> {
        final LogLine emptyLogLine = new LogLine(Long.MIN_VALUE, "", "");
        String[] t = line.split("\\s+");
        try {
            return new LogLine(Long.parseLong(t[0]), t[1], t[2]);
        } catch(NumberFormatException e) {
            return new LogLine(Long.MIN_VALUE, t[1], t[2]);
        } catch(IndexOutOfBoundsException e) {
            return emptyLogLine;
        }
    };

    static Predicate<LogLine> window(final long from, final long to) {
        return log -> log.timestamp() >= from && log.timestamp() <= to;
    }

    public static Predicate<LogLine> connectedTo(final String host) {
        return line -> line.targetHost().equalsIgnoreCase(host) ;
    }

    public static Predicate<LogLine> connectedFrom(final String host) {
        return line -> line.sourceHost().equalsIgnoreCase(host) ;
    }
}
