package com.example.logrestapi.service;

import com.example.logrestapi.model.Log;
import com.example.logrestapi.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService  {
    @Autowired
    private LogRepository logRepository;

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
}
