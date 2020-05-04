#!/bin/sh

IMAGE=ibmmqjmsclient

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

echo "going to build $IMAGE image..."

_pwd=`pwd`
cd $this_folder
mvn clean package -DskipTests

docker build -t $IMAGE .

cd $_pwd

echo "...image build done."