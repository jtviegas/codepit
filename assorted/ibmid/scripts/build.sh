#!/bin/sh
echo "building..."

folder=$(dirname $(readlink -f $0))
base=$(dirname $folder)

BACKEND=$base/backend
BUILD=$base/backend/dist
DIST=$base/dist

echo "...building backend..."
_pwd=`pwd`
rm -rf $DIST
cd $BACKEND
grunt build
if [ "0" != "$?" ]; then
	echo "...backend build failed...leaving!!!"
	cd $_pwd
	return 1
fi
cd $_pwd

mv $BUILD $DIST
echo "...done."


