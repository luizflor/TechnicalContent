package com.example.logrestapi.controller;
import com.example.logrestapi.model.LogJson;
import com.example.logrestapi.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * This is controller to access the MongoDB logJson database  that receives Log4j2 Appender as HTTP by uses PatternLayout
 */

@RestController
@RequestMapping(value = "/api")
public class LogJsonController {

    @Autowired
    private LogService logService;

    private  Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @RequestMapping(value = "/logJson/{id}", method = RequestMethod.GET)
    public Optional<LogJson> getLogLojson(@PathVariable("id") String id){
        logger.info(id);
        return logService.findLogJsonById(id);
    }

    @RequestMapping(value = "/logJson/trackingId/{id}", method = RequestMethod.GET)
    public List<LogJson> getLogJsonByTrackingId(@PathVariable("id") String id){
        logger.info(id);
        return logService.findLogJsonByTrackingId(id);
    }

    @RequestMapping(value = "/logJson/betweenEntryDateTime", method = RequestMethod.GET)
    public List<LogJson> findAllLogJsonBetweenEntryDateTime(@RequestParam Instant beginEntryDateTime, @RequestParam Instant endEntryDateTime){
        logger.info(beginEntryDateTime.toString(),endEntryDateTime.toString());
        return logService.findAllLogJsonBetweenEntryDateTime(beginEntryDateTime,endEntryDateTime);
    }

    @RequestMapping(value = "/logJson/betweenEntryDateTimeCSV", method = RequestMethod.GET)
    public ResponseEntity<String> findAllLogJsonBetweenEntryDateTimeCSV(@RequestParam Instant beginEntryDateTime, @RequestParam Instant endEntryDateTime){
        logger.info(beginEntryDateTime.toString(),endEntryDateTime.toString());
        List<LogJson> logs = logService.findAllLogJsonBetweenEntryDateTime(beginEntryDateTime, endEntryDateTime);
        StringBuilder csv = new StringBuilder();
        csv.append("entryDateTime,")
                .append("level,")
                .append("timestamp,")
                .append("exception,")
                .append("location,")
                .append("processId,")
                .append("threadId,")
                .append("threadName,")
                .append("trackingId,")
                .append("host,")
                .append("logger,")
                .append("message,")
                .append("xclass,")
                .append("id\n");
        logs.forEach(l->{
            csv.append(l.getEntryDateTime())
                    .append(",")
                    .append(l.getLevel().replace(",",";"))
                    .append(",")
                    .append(l.getTimestamp().replace(",",";"))
                    .append(",")
                    .append(l.getException().replace(",",";"))
                    .append(",")
                    .append(l.getLocation().replace(",",";"))
                    .append(",")
                    .append(l.getProcessId().replace(",",";"))
                    .append(",")
                    .append(l.getThreadId().replace(",",";"))
                    .append(",")
                    .append(l.getThreadName().replace(",",";"))
                    .append(",")
                    .append(l.getTrackingId().replace(",",";"))
                    .append(",")
                    .append(l.getHost().replace(",",";"))
                    .append(",")
                    .append(l.getLogger().replace(",",";"))
                    .append(",")
                    .append(l.getMessage().replace(",",";"))
                    .append(",")
                    .append(l.getXClass().replace(",",";"))
                    .append(",")
                    .append(l.getId().replace(",",";"))
                    .append("\n");
        });
        return ResponseEntity.ok().header("Content-Disposition","attachement; filename=logJson.csv").body(csv.toString());
    }
    @RequestMapping(value = "/logJson", method = RequestMethod.POST)
    public LogJson log(@RequestBody String log) throws JsonProcessingException {
        logger.info(log.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String logStr = log.replace("'", "\"")
                .replace("\n", "")
                .replace("\u0009", "")
                .replace("\"class\"","\"xclass\"");
        LogJson logJson = objectMapper.readValue(logStr, LogJson.class);
        return logService.save(logJson);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.ok().body(e.getMessage());
    }
}
