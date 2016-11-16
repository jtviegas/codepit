#!/bin/sh

. ./vars.sh

APP_IMG=$REGISTRY/$APP_NAME
docker build -f $APP_DOCKERFILE -t $APP_IMG .
docker push $APP_IMG

cf ic stop $APP_NAME
cf ic rm $APP_NAME
cf ic run --name $APP_NAME  -p $PORT -m $BLUEMIX_CONTAINER_MEMORY $APP_IMG

#sleep 12
#cf ic ip bind $IP1 $APP_NAME
#cf ic exec -it $APP_NAME /bin/bash
