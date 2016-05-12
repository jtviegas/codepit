@echo off

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> deploying weblabs to openshift..."

set EAR_FILE=weblabs-0.0.1-SNAPSHOT.ear
set EAR_PATH=C:\Users\joao\Documents\i\dev\wxsp\labs\weblabs\target
set PWD=%cd%
set APPNAME=weblabs
set OPENSHIFT_CONNECTION_INFO=535b9635e0b8cde14c000065@weblabs-aprestos.rhcloud.com
set OPENSHIFT_DEPLOYMENT_FOLDER=~/wildfly/standalone/deployments
set SCP_COMMAND=scp %EAR_FILE% %OPENSHIFT_CONNECTION_INFO%:%OPENSHIFT_DEPLOYMENT_FOLDER%
set APP_RESTART_COMMAND=rhc app restart -a %APPNAME%

cd %EAR_PATH%
set errorlevel=
call %SCP_COMMAND%
if "%errorlevel%"=="0" goto COPY_SUCCESS
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... something's wrong deploying weblabs to openshift!"
goto END

:COPY_SUCCESS
cd %PWD%
call %APP_RESTART_COMMAND%
if "%errorlevel%"=="0" goto SUCCESS
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... couldn't restart application after copying it successfully!"
goto END

:SUCCESS
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ... deployed weblabs to openshift successfully!"

:END
sleep 10