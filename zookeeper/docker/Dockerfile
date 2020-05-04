FROM openjdk:8u111-jdk

ENV DEBIAN_FRONTEND noninteractive

ENV ROOT /
ENV BUILD_DIR /tmp
ENV ZK_VERSION "3.4.10"
ENV ZK_BUNDLE zookeeper-$ZK_VERSION.tar.gz
ENV ZK_BUNDLE_DIR zookeeper-$ZK_VERSION
ENV ZK_DIR /opt/zookeeper
ENV ZK_DATA_DIR /opt/data
ENV ZK_CONF_DIR $ZK_DIR/conf
ENV CONF zoo.cfg

# zookeeper client, follower and election ports
ENV ZK_CL_PORT 2181
ENV ZK_FO_PORT 2888
ENV ZK_EL_PORT 3888

RUN mkdir -p $ZK_DATA_DIR

WORKDIR $BUILD_DIR
# Install Kafka, Zookeeper and other needed things
RUN apt-get update && \
    apt-get install -y wget && \ 
    apt-get clean && \ 
    wget --no-cookies --no-check-certificate http://ftp.heanet.ie/mirrors/www.apache.org/dist/zookeeper/$ZK_BUNDLE_DIR/$ZK_BUNDLE -O $ZK_BUNDLE && \ 
    tar xzpvf $ZK_BUNDLE && \ 
    rm $ZK_BUNDLE && \ 
    mv $ZK_BUNDLE_DIR $ZK_DIR

WORKDIR $ZK_DIR
ADD $CONF $ZK_CONF_DIR/$CONF
WORKDIR $ROOT

EXPOSE $ZK_CL_PORT $ZK_FO_PORT $ZK_EL_PORT

ENTRYPOINT ["/opt/zookeeper/bin/zkServer.sh"]
CMD ["start-foreground"]

