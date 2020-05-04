#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
. $this_folder/VARS.sh

echo "going to run container from image $IMAGE with name $NAME ..."
docker run -d --name $NAME -p 8161:8161 -p 61616:61616 $DOCKER_HUB_IMG:$VERSION
echo "... done."