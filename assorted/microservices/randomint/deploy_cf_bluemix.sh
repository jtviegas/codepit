#!/bin/sh

. ./vars.sh

thedir=$(dirname $(readlink -f $0))

_pwd=`pwd`
cd $thedir
cf push $APP_NAME
cd $_pwd
sleep 16
cf logs $APP_NAME


