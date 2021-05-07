package com.example.logrestapi.service;

import com.example.logrestapi.model.Log;

import java.util.List;
import java.util.Optional;

public interface LogService {
    Optional<Log> findById(String id);
    List<Log> findByTrackingId(String trackingId);
    Log save(Log log);
}
