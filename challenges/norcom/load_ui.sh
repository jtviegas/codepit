#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

_pwd=`pwd`

cd $this_folder/ui/
npm start
cd $_pwd
echo "overall outcome: $__r"
