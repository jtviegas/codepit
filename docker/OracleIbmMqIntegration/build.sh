#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

_pwd=`pwd`
cd $this_folder/oracle 
./build.sh
cd $this_folder/ibmmqjmsclient 
./build.sh
cd $_pwd
