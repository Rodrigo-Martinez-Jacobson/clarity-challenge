package com.rodrigomartinez.clarity.unlimitedinputparser;

public class LogLine {
    private final long timestamp;
    private final String sourceHost;
    private final String targetHost;

    public LogLine(final long timestamp, final String sourceHost, final String targetHost) {
        this.timestamp = timestamp;
        this.sourceHost = sourceHost;
        this.targetHost = targetHost;
    }

    long timestamp() { return timestamp; }

    String sourceHost() { return sourceHost; }

    String targetHost() { return targetHost; }
}

