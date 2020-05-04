#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
. $this_folder/VARS.sh

echo "going to run container from image $NAME with name $NAME ..."
docker run -d --name $NAME $DOCKER_HUB_IMG:$VERSION
echo "... done."