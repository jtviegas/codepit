#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

mvn -f $this_folder/backend/pom.xml spring-boot:run

echo "overall outcome: $__r"
