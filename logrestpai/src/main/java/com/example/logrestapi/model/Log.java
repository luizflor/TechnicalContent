package com.example.logrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document
public class Log {
    @Id
    @JsonProperty("id") String id = null;
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private String thread;
    private String level;

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", thread='" + thread + '\'' +
                ", level='" + level + '\'' +
                ", loggerName='" + loggerName + '\'' +
                ", message='" + message + '\'' +
                ", thrown=" + thrown +
                ", endOfBatch=" + endOfBatch +
                ", loggerFqcn='" + loggerFqcn + '\'' +
                ", instant=" + instant +
                ", threadID=" + threadID +
                ", threadPriority=" + threadPriority +
                ", trackingID='" + trackingId + '\'' +
                ", host='" + host + '\'' +
                '}';
    }

    private String loggerName;
    private String message;
    private Thrown thrown;
    private boolean endOfBatch;
    private String loggerFqcn;
    private Instant instant;
    private long threadID;
    private long threadPriority;
    private String trackingId;
    private String host;

    public String getThread() { return thread; }
    public void setThread(String value) { this.thread = value; }

    public String getLevel() { return level; }
    public void setLevel(String value) { this.level = value; }

    public String getLoggerName() { return loggerName; }
    public void setLoggerName(String value) { this.loggerName = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public Thrown getThrown() { return thrown; }
    public void setThrown(Thrown value) { this.thrown = value; }

    public boolean getEndOfBatch() { return endOfBatch; }
    public void setEndOfBatch(boolean value) { this.endOfBatch = value; }

    public String getLoggerFqcn() { return loggerFqcn; }
    public void setLoggerFqcn(String value) { this.loggerFqcn = value; }

    public Instant getInstant() { return instant; }
    public void setInstant(Instant value) { this.instant = value; }

    public long getThreadID() { return threadID; }
    public void setThreadID(long value) { this.threadID = value; }

    public long getThreadPriority() { return threadPriority; }
    public void setThreadPriority(long value) { this.threadPriority = value; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String value) { this.trackingId = value; }

    public String getHost() { return host; }
    public void setHost(String value) { this.host = value; }
}



