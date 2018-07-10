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

echo "going to configure deadlock monitoring..."

DB=$1
echo "connecting to db $DB"
db2 "connect to $DB"
db2 "CREATE TABLESPACE TBSPACE_LOCKING"
db2 "CREATE EVENT MONITOR EVMON_LOCKING FOR LOCKING WRITE TO UNFORMATTED EVENT TABLE (TABLE EMDATA.TAB_LOCKING IN TBSPACE_LOCKING)"
db2 "SET EVENT MONITOR EVMON_LOCKING STATE 1"
db2 "UPDATE DB CFG USING MON_DEADLOCK HISTORY IMMEDIATE"
echo "...configured deadlock monitoring !"
