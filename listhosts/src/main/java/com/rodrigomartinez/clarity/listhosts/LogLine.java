package com.rodrigomartinez.clarity.listhosts;

public class LogLine {
    private final long timestamp;
    private final String sourceHost;
    private final String targetHost;

    public LogLine(final long timestamp, final String sourceHost, final String targetHost) {
        this.timestamp = timestamp;
        this.sourceHost = sourceHost;
        this.targetHost = targetHost;
    }

    public long timestamp() {
        return timestamp;
    }

    public String sourceHost() {
        return sourceHost;
    }

    public String targetHost() {
        return targetHost;
    }
}

