#!/bin/sh

CONTAINER_NAME=mynodeapp
CONTAINER_PORT=8080
HOST_PORT=8080

containerId=`docker ps | grep $CONTAINER_NAME:latest | awk '{print $1;}'`
echo "...container found: $containerId"

if [ ! -z "$containerId" ]; then
	echo "going to stop container $containerId"
    docker stop $containerId
    sleep 10
else
 	echo "no container to stop"
fi


docker rmi --force=true $CONTAINER_NAME
sleep 10

docker build -t $CONTAINER_NAME .
docker run -p=$HOST_PORT:$CONTAINER_PORT -d $CONTAINER_NAME 

sleep 12

containerId=`docker ps | grep $CONTAINER_NAME:latest | awk '{print $1;}'`
echo "...container found: $containerId"
if [ ! -z "$containerId" ]; then
    docker logs $containerId
fi

