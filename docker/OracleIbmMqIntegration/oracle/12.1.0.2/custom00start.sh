#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

echo "=== custom startup shell script === >>"
echo @/opt/oracle/product/12.1.0.2/dbhome_1/mgw/admin/catmgw.sql | sqlplus sys/passw0rd@oracledb as sysdba
echo "=== custom startup script === <<"