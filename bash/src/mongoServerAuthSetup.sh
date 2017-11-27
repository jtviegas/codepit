#!/bin/sh

usage()
{
	cat <<EOM
    	usage: $(basename $0) <password> [<host>]
              password			password for the 'admin' user that is going to
              					have 'userAdminAnyDatabase' role
              host				optional, the mongo host, otherwise will use localhost
EOM
exit 1
}

if [ -z $1 ]  
then
	usage
fi

PSWD=$1
HOST=$2

echo "! going to setup mongo server authentication for user 'admin' with password $PSWD..."

folder=$(dirname $(readlink -f $0))
scriptName=`basename "$0"`
jsFile=$folder/.$scriptName.js

cmd="use admin \n db.addUser({user: 'admin', pwd: '#PSWD#', roles: ['userAdminAnyDatabase']})"
echo "$cmd" | sed -- "s/#PSWD#/$PSWD/g" >  $jsFile

mongo $HOST < $jsFile

rm $jsFile
echo "...done!"