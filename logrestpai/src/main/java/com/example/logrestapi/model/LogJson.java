package com.example.logrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.ZonedDateTime;

@Document(value = "MyLog")
public class LogJson {
    @Id
    @JsonProperty("id") String id = null;
    private ZonedDateTime entryDateTime = ZonedDateTime.now();
    private String level;
    private String timestamp;
    private String XClass;
    private String exception;
    private String location;

    private String processId;

    private String threadId;
    private String threadName;
    private String trackingId;
    private String host;
    private String logger;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() { return level; }
    public void setLevel(String value) { this.level = value; }

    public ZonedDateTime getEntryDateTime() {
        return entryDateTime;
    }
    public void setEntryDateTime(ZonedDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String value) { this.timestamp = value; }

    public String getXClass() { return XClass; }
    public void setXClass(String value) { this.XClass = value; }

    public String getException() { return exception; }
    public void setException(String value) { this.exception = value; }

    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public String getProcessId() { return processId; }
    public void setProcessId(String value) { this.processId = value; }

    public String getThreadId() { return threadId; }
    public void setThreadId(String value) { this.threadId = value; }

    public String getThreadName() { return threadName; }
    public void setThreadName(String value) { this.threadName = value; }

    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String value) { this.trackingId = value; }

    public String getHost() { return host; }
    public void setHost(String value) { this.host = value; }

    public String getLogger() { return logger; }
    public void setLogger(String value) { this.logger = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }
}
