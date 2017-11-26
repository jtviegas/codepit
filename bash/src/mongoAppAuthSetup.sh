#!/bin/sh

usage()
{
	cat <<EOM
    	usage: $(basename $0) <app name> <password> [<host>]
              app name			normally should be the app name, so that 
              					2 db's are created <app name>_db and <app name>_devdb
              password			password for the 'app' user that is going to be created
              					on both db's
              host				optional, the mongo host, otherwise will use localhost
EOM
exit 1
}

folder=$(dirname $(readlink -f $0))
scriptName=`basename "$0"`
jsFile=$folder/.$scriptName.js


if [ -z $1 ] || [ -z $2 ] 
then
	usage
fi

APPNAME=$1
PSWD=$2
HOST=$3

echo "! going to setup db authentication for app $APPNAME with password $PSWD..."

cmd="use #APPNAME#_db \n db.addUser( { user: 'app', pwd: '#PSWD#', roles: [ 'readWrite', 'dbAdmin' ] } ) \n use #APPNAME#_devdb \n db.addUser( { user: 'app', pwd: '#PSWD#', roles: [ 'readWrite', 'dbAdmin' ]  } )"
echo "$cmd" | sed -- "s/#APPNAME#/$APPNAME/g" | sed -- "s/#PSWD#/$PSWD/g" >  $jsFile

mongo $HOST < $jsFile

rm $jsFile
echo "...done!"