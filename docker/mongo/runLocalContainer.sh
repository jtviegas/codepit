#!/bin/sh
. ./VARS.sh
docker run --name $CONTAINER -p $PORT:$PORT -d $IMG

