package com.example.logrestapi.repository;

import com.example.logrestapi.model.Log;
import com.example.logrestapi.model.LogJson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface LogJsonRepository extends MongoRepository<LogJson, String> {
    // db.getCollection('MyLog').find({'trackingId':'758903'})
    List<LogJson> findByTrackingId(String trackingId);
    //db.getCollection('MyLog').find({"entryDateTime" : {$gte:ISODate("2021-06-10T01:03:45.226Z"),  $lte:ISODate("2021-06-10T01:03:45.258Z")}})
//    @Query("{'entryDateTime' : {$gte:ISODate(?0),  $lte:ISODate(?1)}}")
    @Query("{'entryDateTime' : {$gte:?0,  $lte:?1}}")
    List<LogJson> findAllBetweenEntryDateTimeBetween(Instant beginEntryDateTime, Instant endEntryDateTime);

}
