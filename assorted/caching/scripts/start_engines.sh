#!/bin/sh

MONGO_DB_PATH=/var/mongodb/data
MONGO_PID_FILE=/var/mongodb/data/pid
REDIS_CONF_FILE=/etc/redis/redis-dev.conf
NEO4J_BIN=/opt/neo4j/neo4j-community-2.1.5/bin
REDIS_RUN_FOLDER=/tmp/run/redis

if [ ! -d $REDIS_RUN_FOLDER ]; then
	mkdir -p $REDIS_RUN_FOLDER
fi 

mongod --dbpath $MONGO_DB_PATH --pidfilepath $MONGO_PID_FILE &
redis-server $REDIS_CONF_FILE &
# we are currently running it embedded in memory
#$NEO4J_BIN/neo4j start
sleep 12


