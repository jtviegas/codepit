#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)


docker pull docker.elastic.co/elasticsearch/elasticsearch:6.4.0
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "cluster.name=elasticsearch" docker.elastic.co/elasticsearch/elasticsearch:6.4.0

__r=$?
if [ ! "$__r" -eq "0" ] ; then echo "could not run docker container !...leaving." && return 1; fi

echo "overall outcome: $__r"
