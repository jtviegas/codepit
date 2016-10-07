#!/bin/sh

folder=$(dirname $(readlink -f $0))
base=$(dirname $folder)

APP_NAME=ibmidexp
_pwd=`pwd`

cd $base/dist

sleep 16
cf push $APP_NAME --no-start
cf start $APP_NAME

sleep 16 
cf logs $APP_NAME

cd $_pwd
