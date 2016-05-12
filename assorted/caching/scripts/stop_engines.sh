#!/bin/sh

MONGO_PID_FILE=/var/mongodb/data/pid
REDIS_PID_FILE=/var/run/redis/redis-server.pid
NEO4J_BIN=/opt/neo4j/neo4j-community-2.1.5/bin

mongo_pid=`cat $MONGO_PID_FILE`
redis_pid=`cat $REDIS_PID_FILE`

echo "redis pid is $redis_pid"
echo "mongo pid is $mongo_pid"

kill -kill $mongo_pid
kill -kill $redis_pid
# we are currently running it embedded in memory
#$NEO4J_BIN/neo4j stop
