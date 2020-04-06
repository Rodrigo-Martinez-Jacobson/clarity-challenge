package com.rodrigomartinez.clarity.unlimitedinputparser;

import java.util.function.Function;
import java.util.function.Predicate;

public class LogParser {

    public static Function<String, LogLine> parse = line -> {
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

    static Predicate<LogLine> connectedTo(final String host) {
        return line -> line.targetHost().equalsIgnoreCase(host) ;
    }

    static Predicate<LogLine> connectedFrom(final String host) {
        return line -> line.sourceHost().equalsIgnoreCase(host) ;
    }
}
