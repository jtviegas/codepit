#!/usr/bin/env bash

PROJ_NAME=swaggerexp

npm install -g swagger
swagger project create $PROJ_NAME

# load editor
swagger project edit
# running in mock mode
swagger project start $PROJ_NAME -m
# running in ral mode
swagger project start $PROJ_NAME