#!/bin/sh
. ./VARS.sh
docker tag $IMG $BX_IMG
docker push $BX_IMG
