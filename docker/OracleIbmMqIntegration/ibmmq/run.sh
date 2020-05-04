#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

NAME=ibmmq

docker run \
  --env LICENSE=accept \
  --env MQ_QMGR_NAME=$NAME \
  --name $NAME \
  --publish 1414:1414 \
  --publish 9443:9443 \
  --detach \
  ibmcom/mq