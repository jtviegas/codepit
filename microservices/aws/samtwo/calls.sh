#!/bin/sh

sam init --runtime nodejs8.10
cd sam-app
sam build --use-container
sam local start-api
