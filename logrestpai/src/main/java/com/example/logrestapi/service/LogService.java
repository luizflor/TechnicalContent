package com.example.logrestapi.service;

import com.example.logrestapi.model.Log;
import com.example.logrestapi.model.LogJson;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LogService {
    Optional<Log> findById(String id);
    List<Log> findByTrackingId(String trackingId);
    Log save(Log log);

    Optional<LogJson> findLogJsonById(String id);
    List<LogJson> findLogJsonByTrackingId(String trackingId);
    List<LogJson> findAllLogJsonBetweenEntryDateTime(Instant beginEntryDateTime, Instant endEntryDateTime);
    LogJson save(LogJson log);
}
