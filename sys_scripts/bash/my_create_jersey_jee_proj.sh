#!/bin/bash

usage()
{
	cat <<EOM
	usage: $(basename $0) <groupId> <artifactId> <base_package>
EOM
        exit 1
}

if [ -z "$1" ];then
	echo "must provide GroupId"
	usage
fi
groupId=$1
if [ -z "$2" ];then
	echo "must provide artifactId"
	usage
fi
artifactId=$2
if [ -z "$3" ];then
	echo "must provide base_package"
	usage
fi

base_package=$3


mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp -DarchetypeGroupId=org.glassfish.jersey.archetypes \
-DarchetypeVersion=2.26 -DinteractiveMode=false \
-DgroupId=$groupId -DartifactId=$artifactId -Dversion=0.0.1 -Dpackage=$base_package

