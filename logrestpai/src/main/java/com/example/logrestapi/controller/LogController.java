package com.example.logrestapi.controller;

import com.example.logrestapi.model.Log;
import com.example.logrestapi.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<Log> getTrackingId(@PathVariable("id") String id){
        logger.info(id);
        return logService.findByTrackingId(id);
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public Log log(@RequestBody Log log){
        logger.info(log.toString());
        return logService.save(log);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        return ResponseEntity.ok().body(e);
    }
}
