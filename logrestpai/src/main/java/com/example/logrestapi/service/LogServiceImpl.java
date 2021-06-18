package com.example.logrestapi.service;

import com.example.logrestapi.model.Log;
import com.example.logrestapi.model.LogJson;
import com.example.logrestapi.repository.LogJsonRepository;
import com.example.logrestapi.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService  {
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogJsonRepository logJsonRepository;

    @Override
    public Optional<Log> findById(String id) {
        return logRepository.findById(id);
    }

    @Override
    public List<Log> findByTrackingId(String trackingId) {
        return logRepository.findByTrackingId(trackingId);
    }

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }
    @Override
    public Optional<LogJson> findLogJsonById(String id) {
        return logJsonRepository.findById(id);
    }

    @Override
    public List<LogJson> findLogJsonByTrackingId(String trackingId) {
        return logJsonRepository.findByTrackingId(trackingId);
    }
    public List<LogJson> findAllLogJsonBetweenEntryDateTime(Instant beginEntryDateTime, Instant endEntryDateTime){
        return logJsonRepository.findAllBetweenEntryDateTimeBetween(beginEntryDateTime,endEntryDateTime);
    }

    @Override
    public LogJson save(LogJson log) {
        return logJsonRepository.save(log);
    }
}
