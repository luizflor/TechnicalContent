# Using Log4j to append message into Mongo DB via HttpAppender

# Log4j Documentation
https://logging.apache.org/log4j/2.x/manual/layouts.html#JSONLayout

# requirements
1. Log4j requires certificate
```
    <SSL>
        <TrustStore location="./certificates/root.jks"  password="trustpass"/>
    </SSL>
```
2. Appender uses JsonLayout. 
```
    <JsonLayout complete="false" compact="false">
        <KeyValuePair key="trackingId" value="$${ctx:trackingId}" />
        <KeyValuePair key="host" value="$${hostName}" />
    </JsonLayout>
```

# API Sample
```
https://localhost:25001/api/log/609564ba3d3da419a07d7171
https://localhost:25001/api/log/trackingId/773560
```
