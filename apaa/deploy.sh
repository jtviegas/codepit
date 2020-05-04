#!/bin/sh

mvn clean package
cf push apaa -p target/apaa.ear