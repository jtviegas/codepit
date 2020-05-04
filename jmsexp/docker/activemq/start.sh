#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)
_pwd=`pwd`

cd $this_folder/bin
./activemq console

cd $_pwd
