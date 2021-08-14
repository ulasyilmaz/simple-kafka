# simple-kafka

This application contains a Kafka producer that passes data to a Kafka consumer over a Kafka
cluster. The data produced by the producer is the current position of the International Space
Station (ISS). The consumer prints out the result as a log.

## Open Notify
_Open Notify is an open source project to provide a simple programming interface for some of NASA’s awesome data._
For further information, please refer to [Open-Notify-API](https://github.com/open-notify/Open-Notify-API) on GitHub. 
In the scope of this project, the API endpoint [iss-now.json](http://api.open-notify.org/iss-now.json) is used to fetch ISS position data, in order to generate on-the-fly data for Kafka producer. 

## Kafka Cluster

Start the Kafka Cluster by running the service **kafka**
in [docker-compose.yaml](docker-compose.yml) from IntelliJ.

Alternatively, you can run the following CLI command in the project root.

````shell
docker-compose up 
````

## Local Deployment

Having started the Kafka cluster, you can deploy **simple-kafka** to local by simply running the service
in [SimpleKafkaApplication](src/main/java/org/improvisations/simplekafka/SimpleKafkaApplication.java)
.

Alternatively, you can run the following CLI command in the project root.

````shell
./gradlew bootRun
````

Logs will flow as follow:
````shell
2021-08-14 22:37:57.056  INFO 1760 --- [ctor-http-nio-3] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.7.1
2021-08-14 22:37:57.056  INFO 1760 --- [ctor-http-nio-3] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 61dbce85d0d41457
2021-08-14 22:37:57.056  INFO 1760 --- [ctor-http-nio-3] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1628973477056
2021-08-14 22:37:57.071  INFO 1760 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: YueSR6mLToejq2t3moluzg
2021-08-14 22:37:59.560  INFO 1760 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-positions-1, groupId=positions] Successfully joined group with generation Generation{generationId=39, memberId='consumer-positions-1-1fda44f4-c680-4cda-b3ee-8b50d6056f84', protocol='range'}
2021-08-14 22:37:59.562  INFO 1760 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-positions-1, groupId=positions] Finished assignment for group at generation 39: {consumer-positions-1-1fda44f4-c680-4cda-b3ee-8b50d6056f84=Assignment(partitions=[iss-position-0])}
2021-08-14 22:37:59.572  INFO 1760 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-positions-1, groupId=positions] Successfully synced group in generation Generation{generationId=39, memberId='consumer-positions-1-1fda44f4-c680-4cda-b3ee-8b50d6056f84', protocol='range'}
2021-08-14 22:37:59.572  INFO 1760 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-positions-1, groupId=positions] Notifying assignor about the new Assignment(partitions=[iss-position-0])
2021-08-14 22:37:59.575  INFO 1760 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-positions-1, groupId=positions] Adding newly assigned partitions: iss-position-0
2021-08-14 22:37:59.589  INFO 1760 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-positions-1, groupId=positions] Setting offset for partition iss-position-0 to the committed offset FetchPosition{offset=71, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[localhost:29092 (id: 1 rack: null)], epoch=0}}
2021-08-14 22:37:59.590  INFO 1760 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : positions: partitions assigned: [iss-position-0]
2021-08-14 22:38:02.278  INFO 1760 --- [ctor-http-nio-3] org.dilmac.simplekafka.kafka.Producer    : Successfully retrieved ISS data from open-notify: IssNow(issPosition=IssNow.IssPosition(longitude=120.8161, latitude=-22.1051), message=success, timestamp=1628973481)
2021-08-14 22:38:02.284  INFO 1760 --- [ad | producer-1] org.dilmac.simplekafka.kafka.Producer    : Successfully sent message to iss-position::positions: SendResult [producerRecord=ProducerRecord(topic=iss-position, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = __TypeId__, value = [111, 114, 103, 46, 100, 105, 108, 109, 97, 99, 46, 115, 105, 109, 112, 108, 101, 107, 97, 102, 107, 97, 46, 100, 111, 109, 97, 105, 110, 46, 73, 115, 115, 78, 111, 119])], isReadOnly = true), key=null, value=IssNow(issPosition=IssNow.IssPosition(longitude=120.8161, latitude=-22.1051), message=success, timestamp=1628973481), timestamp=null), recordMetadata=iss-position-0@72]
2021-08-14 22:38:02.289  INFO 1760 --- [ntainer#0-0-C-1] o.d.simplekafka.service.ConsumerService  : Successfully received message from iss-position::positions: IssNow(issPosition=IssNow.IssPosition(longitude=120.8161, latitude=-22.1051), message=success, timestamp=1628973481)
2021-08-14 22:38:07.465  INFO 1760 --- [ctor-http-nio-3] org.dilmac.simplekafka.kafka.Producer    : Successfully retrieved ISS data from open-notify: IssNow(issPosition=IssNow.IssPosition(longitude=121.0284, latitude=-21.8649), message=success, timestamp=1628973486)
2021-08-14 22:38:07.471  INFO 1760 --- [ad | producer-1] org.dilmac.simplekafka.kafka.Producer    : Successfully sent message to iss-position::positions: SendResult [producerRecord=ProducerRecord(topic=iss-position, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = __TypeId__, value = [111, 114, 103, 46, 100, 105, 108, 109, 97, 99, 46, 115, 105, 109, 112, 108, 101, 107, 97, 102, 107, 97, 46, 100, 111, 109, 97, 105, 110, 46, 73, 115, 115, 78, 111, 119])], isReadOnly = true), key=null, value=IssNow(issPosition=IssNow.IssPosition(longitude=121.0284, latitude=-21.8649), message=success, timestamp=1628973486), timestamp=null), recordMetadata=iss-position-0@73]
2021-08-14 22:38:07.475  INFO 1760 --- [ntainer#0-0-C-1] o.d.simplekafka.service.ConsumerService  : Successfully received message from iss-position::positions: IssNow(issPosition=IssNow.IssPosition(longitude=121.0284, latitude=-21.8649), message=success, timestamp=1628973486)
2021-08-14 22:38:12.750  INFO 1760 --- [ctor-http-nio-3] org.dilmac.simplekafka.kafka.Producer    : Successfully retrieved ISS data from open-notify: IssNow(issPosition=IssNow.IssPosition(longitude=121.261, latitude=-21.6003), message=success, timestamp=1628973492)
2021-08-14 22:38:12.756  INFO 1760 --- [ad | producer-1] org.dilmac.simplekafka.kafka.Producer    : Successfully sent message to iss-position::positions: SendResult [producerRecord=ProducerRecord(topic=iss-position, partition=null, headers=RecordHeaders(headers = [RecordHeader(key = __TypeId__, value = [111, 114, 103, 46, 100, 105, 108, 109, 97, 99, 46, 115, 105, 109, 112, 108, 101, 107, 97, 102, 107, 97, 46, 100, 111, 109, 97, 105, 110, 46, 73, 115, 115, 78, 111, 119])], isReadOnly = true), key=null, value=IssNow(issPosition=IssNow.IssPosition(longitude=121.261, latitude=-21.6003), message=success, timestamp=1628973492), timestamp=null), recordMetadata=iss-position-0@74]
2021-08-14 22:38:12.761  INFO 1760 --- [ntainer#0-0-C-1] o.d.simplekafka.service.ConsumerService  : Successfully received message from iss-position::positions: IssNow(issPosition=IssNow.IssPosition(longitude=121.261, latitude=-21.6003), message=success, timestamp=1628973492)
````

## License
Copyright (c) 2021, Ulaş Yılmaz

This program is free software: You are welcome to copy, redistribute or modify it under the terms of [GNU General Public License](LICENSE).

Please note that this program is not designed for commercial usage or for critical systems.
It is an overly simplified version of distributed event streaming platform Apache Kafka® for beginners.
It comes without any warranty, merchantability or usage for a particular purpose.

I can only hope it gives you a boost during your first encounter with event streams.

## References

1. [Open-Notify-API](https://github.com/open-notify/Open-Notify-API)
2. [Apache Kafka® Fundamentals](https://www.youtube.com/watch?v=-DyWhcX3Dpc&list=PLa7VYi0yPIH2PelhRHoFR5iQgflg-y6JA)
3. [Kafka: The Definitive Guide, 2nd Edition](https://en.de1lib.org/book/5555452/ee7346)
4. [Intro to Apache Kafka with Spring](https://www.baeldung.com/spring-kafka)
5. [Testing Kafka and Spring Boot](https://www.baeldung.com/spring-boot-kafka-testing)