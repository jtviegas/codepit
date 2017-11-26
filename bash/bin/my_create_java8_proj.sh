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


mvn archetype:generate -B -DarchetypeGroupId=pl.org.miki -DarchetypeArtifactId=java8-quickstart-archetype -DarchetypeVersion=1.0.0 \
-DgroupId=$groupId -DartifactId=$artifactId -Dversion=0.0.1 -Dpackage=$base_package

