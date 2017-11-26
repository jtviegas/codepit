#!/bin/sh

usage()
{
        cat <<EOM
        usage:
        $(basename $0) <database>

EOM
        exit 1
}

[ -z $1 ] && { usage; }

DB=$1
echo "going to remove deadlock monitoring config in database $DB..."
db2 "connect to $DB"
db2 "SET EVENT MONITOR EVMON_LOCKING STATE 0"
db2 "DROP EVENT MONITOR EVMON_LOCKING"
db2 "DROP TABLE EMDATA.TAB_LOCKING"
echo "...done !"

