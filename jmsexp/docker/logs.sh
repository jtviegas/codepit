#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

docker-compose -f $this_folder/docker-compose.yml logs -f
