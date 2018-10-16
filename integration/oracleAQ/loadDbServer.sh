#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

NAME=oracledb
VERSION=12.2.0.1
BUNDLE=ee
DB_PSWD=passw0rd
DB_SID=oracledb


docker run -d --name $NAME -p 1521:1521 -p 5500:5500 -e ORACLE_SID=$DB_SID -e ORACLE_PWD=$DB_PSWD oracle/database:$VERSION-$BUNDLE

