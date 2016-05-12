#!/bin/sh

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> deploying weblabs to openshift..."

EAR_FILE=weblabs-0.0.1-SNAPSHOT.ear
EAR_PATH=/home/joaovieg/workspace/eclipse/dev/labs/weblabs/target
_PWD=`pwd`
APPNAME=weblabs
OPENSHIFT_CONNECTION_INFO=535b9635e0b8cde14c000065@weblabs-aprestos.rhcloud.com
OPENSHIFT_DEPLOYMENT_FOLDER=~/wildfly/standalone/deployments
SCP_COMMAND=scp $EAR_FILE $OPENSHIFT_CONNECTION_INFO:$OPENSHIFT_DEPLOYMENT_FOLDER
APP_RESTART_COMMAND=rhc app restart -a $APPNAME

function deploy_success {
	echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... deployed weblabs to openshift successfully!"	
}
function deploy_failure {
	echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... something's wrong deploying weblabs to openshift!"	
}
function restart_failure {
	echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... couldn't restart application after copying it successfully!"
}

cd $EAR_PATH
set errorlevel=
$SCP_COMMAND

if [[ "$?"=="0" ]]; then
	cd $_PWD
	$APP_RESTART_COMMAND
	if [[ "$?"=="0" ]] ; then 
		deploy_success
	else
		restart_failure
	fi  
else
	deploy_failure
fi

sleep 10s
