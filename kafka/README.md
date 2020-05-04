# kafka
## kafka docker image

- hostname: kafka
- exposes ports: 9092
- expects to resolve zookeeper on "zookeeper" hostname
- automatic creation of topics from received messages

### usage:

- edit VARS.sh accordingly;
- ``` scripts/build.sh ``` - build docker image and push it to dockerHub (/kakicode/kafka) ; 
- ``` scripts/run.sh ``` - run on local docker engine ;

### to test the broker

- attach to the kafka container: ``` docker exec -it kafka /bin/bash ``` ;
- send a message: ``` /opt/kafka# bin/kafka-console-producer.sh --broker-list kafka:9092 --topic test ``` ;
- consume the message: ``` /opt/kafka# bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic test --from-beginning ``` ;
- see [kafka examples](https://kafka.apache.org/quickstart) ;




