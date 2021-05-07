package com.example.logrestapi.repository;

import com.example.logrestapi.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
    List<Log> findByTrackingId(String trackingId);
}
