```
docker exec -it kafka-mysql-1 /bin/bash

mysql --user=root --password=$MYSQL_ROOT_PASSWORD
use eventuate;
select * from message;
```

you can see:
```
+-----------------------------------+-------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+---------+-----------+-------------------+---------------+
| id                                | destination                   | headers                                                                                                                                                                                                                                                                               | payload | published | message_partition | creation_time |
+-----------------------------------+-------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+---------+-----------+-------------------+---------------+
| 00000183c7c93c05-f2d3907995f30000 | labshopeventuate.domain.Order | {"PARTITION_ID":"1","event-aggregate-type":"labshopeventuate.domain.Order","DATE":"Tue, 11 Oct 2022 16:03:17 GMT","event-aggregate-id":"1","event-type":"labshopeventuate.domain.OrderPlaced","DESTINATION":"labshopeventuate.domain.Order","ID":"00000183c7c93c05-f2d3907995f30000"} | {}      |         0 |              NULL | 1665504197650 |
+-----------------------------------+-------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+---------+-----------+-------------------+---------------+
```

you can find the kafka log by hitting:

```
docker exec -it kafka-kafka-1 /bin/bash
cd /bin

kafka-topics --bootstrap-server=localhost:9092 --list
kafka-console-consumer --bootstrap-server=localhost:9092 --topic labshopeventuate.domain.Order --from-beginning
```

you can see:
```
{"payload":"{}","headers":{"PARTITION_ID":"1","event-aggregate-type":"labshopeventuate.domain.Order","DATE":"Tue, 11 Oct 2022 16:03:17 GMT","event-aggregate-id":"1","event-type":"labshopeventuate.domain.OrderPlaced","DESTINATION":"labshopeventuate.domain.Order","ID":"00000183c7c93c05-f2d3907995f30000"}}
```


Possible Errors:

- org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'domainEventDispatcher' defined in labshopeventuate.InventoryApplication: Unsatisfied dependency expressed through method 'domainEventDispatcher' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'domainEventDispatcherFactory' defined in class path resource [io/eventuate/tram/spring/events/subscriber/TramEventSubscriberConfiguration.class]: Unsatisfied dependency expressed through method 'domainEventDispatcherFactory' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'messageConsumer' defined in class path resource [io/eventuate/tram/spring/consumer/common/TramConsumerCommonConfiguration.class]: Unsatisfied dependency expressed through method 'messageConsumer' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'messageConsumerImplementation' defined in class path resource [io/eventuate/tram/spring/consumer/kafka/EventuateTramKafkaMessageConsumerConfiguration.class]: Unsatisfied dependency expressed through method 'messageConsumerImplementation' parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'messageConsumerKafka' defined in class path reso

- when you missed to configure eventuate to point to the kafka in the application.yaml