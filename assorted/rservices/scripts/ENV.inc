NAME=rservices
IMG=$NAME
IMG_VERSION=latest
CONTAINER=$IMG
HOST=$NAME


BX_REGISTRY=registry.ng.bluemix.net/mynodeappbue
BLUEMIX_IMG=$BX_REGISTRY/$IMG
BX_CONTAINER_MEMORY=128

DOCKER_HUB_IMG=kakicode/$NAME

KAFKA_CONTAINER=kafka
KAFKA_HOST=kafka

ZK_CONTAINER=zookeeper
ZK_HOST=zookeeper

TOPIC_IN="points-processed"
TOPIC_OUT="points-processed-r"

