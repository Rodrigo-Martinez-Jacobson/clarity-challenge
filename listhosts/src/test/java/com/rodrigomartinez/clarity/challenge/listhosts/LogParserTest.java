package com.rodrigomartinez.clarity.challenge.listhosts;

import com.rodrigomartinez.clarity.listhosts.LogLine;
import com.rodrigomartinez.clarity.listhosts.LogParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;


import static com.rodrigomartinez.clarity.listhosts.LogParser.connectedFrom;
import static com.rodrigomartinez.clarity.listhosts.LogParser.connectedTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogParserTest {
    static LogLine logLine;
    @BeforeAll
    public static void parseLine() {
        logLine = new LogLine(1586165012143L ,"Rayman" ,"Cortana");
    }

    @Test
    @DisplayName("Test if LogParser can read a file")
    public void testFromFileCanReadFile() {
        final Supplier<Stream<String>> lines =
                LogParser.fromFile(new File(getClass().getClassLoader().getResource("input-file-10000_4.txt").getPath()));
        Assertions.assertTrue(lines.get().count() > 0);
    }

    @Test
    @DisplayName("Test if LogParser can parse a log line")
    public void testParseLogLineIsValid() {
        assertEquals("Rayman", logLine.sourceHost());
        assertEquals("Cortana", logLine.targetHost());
        assertEquals(Long.parseLong("1586165012143"), logLine.timestamp());
    }

    @Test
    @DisplayName("Test if connectedTo predicate is correct")
    public void testConnectedToIsValid() {
        Predicate<LogLine> toHost = connectedTo("Cortana");
        assertTrue(toHost.test(logLine));
    }

    @Test
    @DisplayName("Test if connectedFrom predicate is correct")
    public void testConnectedFromIsValid() {
        Predicate<LogLine> fromHost = connectedFrom("Rayman");
        assertTrue(fromHost.test(logLine));
    }
}
