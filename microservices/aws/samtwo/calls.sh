#!/bin/sh

sam init --runtime nodejs8.10
cd sam-app
sam build --use-container
sam local start-api

aws s3 mb s3://samtwo
sam package --output-template-file packaged.yaml --s3-bucket samtwo

sam deploy --template-file packaged.yaml --stack-name sam-app --capabilities CAPABILITY_IAM --region eu-west-1
