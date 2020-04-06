package com.rodrigomartinez.clarity.listhosts;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rodrigomartinez.clarity.listhosts.LogParser.connectedTo;
import static com.rodrigomartinez.clarity.listhosts.LogParser.fromFile;
import static com.rodrigomartinez.clarity.listhosts.LogParser.parse;
import static com.rodrigomartinez.clarity.listhosts.LogParser.window;


public class ListConnectedHosts {
    private Predicate<LogLine> inWindow;
    private Predicate<LogLine> connectedTo;
    private Supplier<Stream<String>> lines;


    public ListConnectedHosts(final String path,
                              final String initDate,
                              final String endDate,
                              final String hostName) {

        // Parse cmdline args
        inWindow = window(parseDate(initDate), parseDate(endDate));
        connectedTo = connectedTo(hostName);
        lines = fromFile(new File(path));

    }

    public Set<String> processLog() {
        return lines
                .get()
                .parallel()
                .map(parse)
                .filter(inWindow)
                .filter(connectedTo)
                .map(LogLine::sourceHost)
                .collect(Collectors.toSet());
    }

    private long parseDate(String date) {
        return LocalDateTime
                .parse( date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
    }
}
