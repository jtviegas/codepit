#!/bin/sh

. ./vars.sh

APP_IMG=$REGISTRY/$APP_NAME
docker build -f $APP_DOCKERFILE -t $APP_IMG .

docker stop $APP_NAME
docker rm $APP_NAME
docker run -d --name $APP_NAME  -p $LOCAL_PORT:$LOCAL_DOCKER_PORT $APP_IMG

#sleep 12
#docker exec -it $APP_NAME /bin/bash
