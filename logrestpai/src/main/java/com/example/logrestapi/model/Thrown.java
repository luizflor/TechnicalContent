package com.example.logrestapi.model;

public class Thrown {
    private long commonElementCount;
    private String localizedMessage;
    private String message;
    private String name;
    private ExtendedStackTrace[] extendedStackTrace;

    public long getCommonElementCount() { return commonElementCount; }
    public void setCommonElementCount(long value) { this.commonElementCount = value; }

    public String getLocalizedMessage() { return localizedMessage; }
    public void setLocalizedMessage(String value) { this.localizedMessage = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public ExtendedStackTrace[] getExtendedStackTrace() { return extendedStackTrace; }
    public void setExtendedStackTrace(ExtendedStackTrace[] value) { this.extendedStackTrace = value; }
}
