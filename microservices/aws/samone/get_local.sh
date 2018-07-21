#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
#. $this_folder/VARS.sh

curl  http://127.0.0.1:3000/
