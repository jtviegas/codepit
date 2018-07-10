#!/bin/sh

#	check
#	https://console.ng.bluemix.net/docs/cli/index.html#cli 
# 	on how to install bluemix cli tools

LOCALDIR=$(dirname $(readlink -f $0))
. $LOCALDIR/commons.sh

defaultSpace=devel
defaultOrg=techdays
internalApi=https://api.stage1.ng.bluemix.net
publicApi=https://api.ng.bluemix.net

#defaults
api=$publicApi
org=$defaultOrg
space=$defaultSpace
user=joaovieg@ie.ibm.com

ousage()
{
        cat <<EOM
        usage:
        $(basename $0)  <space>(default=devel) <organisation>(default=techdays) <api>(default=api.ng.bluemix.net)

        available apis:
        	internalApi: 	https://api.stage1.ng.bluemix.net
        	publicApi: 		https://api.ng.bluemix.net
EOM
}

echo "...remember usage if you want to change default parameters... "
ousage

if [ -z $user ]
then
	echo " !!! please proviude user in the script variables section !!! leaving..."
	exit 1
fi


if [ ! -z $1 ]
then
	space=$1
fi

if [ ! -z $2 ]
then
	org=$2
fi

if [ ! -z $3 ]
then
	api=$3
fi

echo "...you might need to provide a password..."
pswd=$(getPswd)
echo " loging into space: $space & organisation: $org & api: $api"

cf login -a $api -u $user -p $pswd -o $org -s $space
bluemix login -a $api -u $user -p $pswd -o $org -s $space
cf ic login


