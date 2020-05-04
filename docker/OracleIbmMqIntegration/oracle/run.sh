#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
. $this_folder/VARS

docker run -d --name $NAME -p 1521:1521 -p 5500:5500 -e ORACLE_SID=$DB_SID -e ORACLE_PWD=$DB_PSWD -v /opt/oracle/scripts/startup oracle/database:$VERSION-$BUNDLE

