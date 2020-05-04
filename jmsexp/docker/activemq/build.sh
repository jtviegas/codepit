#!/bin/sh

this_folder=$(dirname $(readlink -f $0))

# include file
. $this_folder/VARS.sh

echo "going to build image $IMAGE and push it to docker hub..."

_pwd=`pwd`

cd $this_folder
docker rmi $IMAGE:$VERSION
docker build -t $IMAGE:$VERSION .
docker tag $IMAGE:$VERSION $DOCKER_HUB_IMG:$VERSION
docker push $DOCKER_HUB_IMG

cd $_pwd

echo "... done."