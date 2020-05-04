#!/bin/sh

scripts_folder=$(dirname $(readlink -f $0))
base_folder=$(dirname $scripts_folder)

. $scripts_folder/VARS.sh

docker stop $CONTAINER
docker rm $CONTAINER

#docker run -d -h $HOST --name $CONTAINER  -p $ZK_CL_PORT:$ZK_CL_PORT -p $ZK_FO_PORT:$ZK_FO_PORT -p $ZK_EL_PORT:$ZK_EL_PORT $IMAGE:$IMAGE_VERSION
docker run -d -h $HOST --name $CONTAINER $DOCKER_HUB_IMG:$IMAGE_VERSION
#docker logs -f $CONTAINER