package com.example.logrestapi.model;

public class Instant {
    private long epochSecond;
    private long nanoOfSecond;

    public long getEpochSecond() { return epochSecond; }
    public void setEpochSecond(long value) { this.epochSecond = value; }

    public long getNanoOfSecond() { return nanoOfSecond; }
    public void setNanoOfSecond(long value) { this.nanoOfSecond = value; }
}
