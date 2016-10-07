#!/bin/sh

echo "running..."
folder=$(dirname $(readlink -f $0))
base=$(dirname $folder)

DIST=$base/dist
_pwd=`pwd`
cd $DIST
echo "...retriving modules..."
npm install --production
cd $_pwd
echo "...starting..."
node $DIST/index.js


