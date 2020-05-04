FROM openjdk:8u111-jdk

ENV DEBIAN_FRONTEND noninteractive

ENV ROOT /
ENV BUILD_DIR /tmp
ENV SCALA_VERSION 2.11
ENV KFK_VERSION 0.11.0.2
ENV KFK_BUNDLE kafka_$SCALA_VERSION-$KFK_VERSION.tgz
ENV KFK_BUNDLE_DIR kafka_$SCALA_VERSION-$KFK_VERSION
ENV KFK_DIR /opt/kafka
ENV DWNL_FOLDER_LINK http://www-us.apache.org/dist/kafka/$KFK_VERSION
ENV START_SCRIPT bin/kafka-server-start.sh
ENV LOG_DIR /var/log/kafka
ENV CONF kafka.properties
ENV PORT 9092

RUN mkdir -p $LOG_DIR

WORKDIR $BUILD_DIR
# Install Kafka and other needed things
RUN apt-get update && \
    apt-get install -y wget && \ 
    apt-get clean && \ 
    wget --no-cookies --no-check-certificate $DWNL_FOLDER_LINK/$KFK_BUNDLE -O $KFK_BUNDLE && \ 
    tar xzpvf $KFK_BUNDLE && \ 
    rm $KFK_BUNDLE && \ 
    mv $KFK_BUNDLE_DIR $KFK_DIR

ADD $CONF $KFK_DIR

EXPOSE $PORT
WORKDIR $KFK_DIR
ENTRYPOINT ["/opt/kafka/bin/kafka-server-start.sh"] 
CMD ["/opt/kafka/kafka.properties"]
