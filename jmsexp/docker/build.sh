#!/bin/sh

this_folder=$(dirname $(readlink -f $0))

echo "going to build images and push them to docker hub..."

_pwd=`pwd`

cd $this_folder/activemq
./build.sh
cd $this_folder/producer
./build.sh
cd $this_folder/consumer
./build.sh

cd $_pwd

echo "... done."