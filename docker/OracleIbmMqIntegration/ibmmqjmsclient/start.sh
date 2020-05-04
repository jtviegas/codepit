#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

JAR=ibmmqjmsclient-0.0.1-SNAPSHOT.jar

java -jar $this_folder/$JAR
