@echo off
setlocal EnableDelayedExpansion

SET p=%~dp0
SET thisdir=%p:~0,-1%
FOR %%a IN ("%thisdir%") DO SET p=%%~dpa
set parentdir=%p:~0,-1%

echo %thisdir%
echo %parentdir%

rem include some file
rem call %thisdir%\VARS.cmd

echo '{"httpMethod": "POST", "body": "{\"vote\": \"spaces\"}"}' | TABLE_NAME="vote-spaces-tabs" sam local invoke "VoteSpacesTabs"