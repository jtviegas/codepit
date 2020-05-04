#!/bin/sh

scripts_folder=$(dirname $(readlink -f $0))
base_folder=$(dirname $scripts_folder)

. $scripts_folder/VARS.sh

docker stop $CONTAINER
docker rm $CONTAINER

docker run -d -h $HOST --name $CONTAINER  --link $ZK_CONTAINER:$ZK_HOST $DOCKER_HUB_IMG:$IMAGE_VERSION
#docker logs -f $CONTAINER