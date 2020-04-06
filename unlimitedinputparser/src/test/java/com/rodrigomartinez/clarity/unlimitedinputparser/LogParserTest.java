package com.rodrigomartinez.clarity.unlimitedinputparser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static com.rodrigomartinez.clarity.unlimitedinputparser.LogParser.connectedFrom;
import static com.rodrigomartinez.clarity.unlimitedinputparser.LogParser.connectedTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogParserTest {
    static LogLine logLine;
    @BeforeAll
    public static void parseLine() {
        logLine = LogParser.parse.apply("1586165012143 Rayman Cortana");
    }

    @Test
    @DisplayName("Test if LogParser can parse a log line")
    public void parseLogLine() {
        assertEquals("Rayman", logLine.sourceHost());
        assertEquals("Cortana", logLine.targetHost());
        assertEquals(Long.parseLong("1586165012143"), logLine.timestamp());
    }

    @Test
    @DisplayName("Test if connectedTo predicate is correct")
    public void checkConnectedTo() {
        Predicate<LogLine> toHost = connectedTo("Cortana");
        assertTrue(toHost.test(logLine));
    }

    @Test
    @DisplayName("Test if connectedFrom predicate is correct")
    public void checkConnectedFrom() {
        Predicate<LogLine> fromHost = connectedFrom("Rayman");
        assertTrue(fromHost.test(logLine));
    }
}
