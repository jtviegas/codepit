@echo off

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> undeploying weblabs in openshift..."

set EAR_FILE=weblabs-0.0.1-SNAPSHOT.ear
set DEPLOYED_MARKER_FILE=%EAR_FILE%.deployed

set OPENSHIFT_CONNECTION_INFO=535b9635e0b8cde14c000065@weblabs-aprestos.rhcloud.com
set OPENSHIFT_DEPLOYMENT_FOLDER=~/wildfly/standalone/deployments

set SSH_COMMAND=ssh %OPENSHIFT_CONNECTION_INFO% rm -f %OPENSHIFT_DEPLOYMENT_FOLDER%/%EAR_FILE%*
set errorlevel=
call %SSH_COMMAND%
if "%errorlevel%"=="0" goto SUCCESS
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... something's wrong undeploying weblabs in openshift!"
goto END

:SUCCESS
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... undeployed weblabs in openshift!"

:END
cd %PWD%
sleep 10