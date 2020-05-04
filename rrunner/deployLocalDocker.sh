#!/bin/sh

. ./VARS.sh

docker build -t $IMG .

docker run -it --name $CONTAINER $IMG /bin/sh
 