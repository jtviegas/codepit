#!/bin/sh

APP_NAME=ssoexp

#cf delete --f --r $APP_NAME
sleep 16 
cf push $APP_NAME --no-start
cf start $APP_NAME

sleep 16 
cf logs $APP_NAME --recent
