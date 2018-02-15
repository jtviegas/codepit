#!/bin/sh

echo "going to build image "

scripts_folder=$(dirname $(readlink -f $0))
base_folder=$(dirname $scripts_folder)

. $scripts_folder/ENV.inc

echo "going to build image $IMG and push it to docker hub and bluemix repository..."

_pwd=`pwd`
cd $base_folder

docker rmi $IMG:$IMG_VERSION
docker build -t $IMG $base_folder
docker tag $IMG $DOCKER_HUB_IMG
docker tag $IMG $BLUEMIX_IMG
docker push $DOCKER_HUB_IMG
docker push $BLUEMIX_IMG

cd $_pwd

echo "... done."