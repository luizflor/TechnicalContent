package com.example.logrestapi.controller;

import com.example.logrestapi.model.Log;
import com.example.logrestapi.model.LogJson;
import com.example.logrestapi.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This is controller to access the MongoDB log database  that receives Log4j2 HTTP Appender and pattern JsonLayout
 * The JsonLayout seems fixed amount variables. The challenge is about how can extract elements on log that are not fields captured by Log4j2
 */
@RestController
@RequestMapping(value = "/api")
public class LogController {

    @Autowired
    private LogService logService;

    private  Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Optional<Log> getLog(@PathVariable("id") String id){
        logger.info(id);
        return logService.findById(id);
    }

    @RequestMapping(value = "/log/trackingId/{id}", method = RequestMethod.GET)
    public List<Log> getLogTrackingId(@PathVariable("id") String id){
        logger.info(id);
        return logService.findByTrackingId(id);
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public Log log(@RequestBody Log log){
        logger.info(log.toString());
        return logService.save(log);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.ok().body(e.getMessage());
    }
}