#!/bin/sh

usage()
{
        cat <<EOM
        usage:
        $(basename $0) <database> <user> <password>

EOM
        exit 1
}

( [ -z $1 ] || [ -z $2 ]  || [ -z $3 ] ) && { usage; }

DB=$1
USER=$2
PASS=$3

echo "...retrieving deadlock monitoring info from database $DB..."
java db2evmonfmt -d $DB -ue emdata.tab_locking -type deadlock -ftext  -u $USER -p $PASS
