package com.example.logrestapi.model;

public class ExtendedStackTrace {
    private String extendedStackTraceClass;
    private String method;
    private String file;
    private long line;
    private boolean exact;
    private String location;
    private String version;

    public String getExtendedStackTraceClass() { return extendedStackTraceClass; }
    public void setExtendedStackTraceClass(String value) { this.extendedStackTraceClass = value; }

    public String getMethod() { return method; }
    public void setMethod(String value) { this.method = value; }

    public String getFile() { return file; }
    public void setFile(String value) { this.file = value; }

    public long getLine() { return line; }
    public void setLine(long value) { this.line = value; }

    public boolean getExact() { return exact; }
    public void setExact(boolean value) { this.exact = value; }

    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }
}
