#!/bin/sh
. ./VARS.sh
cf ic run --name $CONTAINER -p $PORT -m $BX_CONTAINER_MEMORY $BX_IMG
sleep 30
cf ic ip bind $BX_IP $CONTAINER
