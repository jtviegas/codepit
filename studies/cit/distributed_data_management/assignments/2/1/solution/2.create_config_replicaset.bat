@echo off
setlocal EnableDelayedExpansion

SET p=%~dp0
SET thisdir=%p:~0,-1%
FOR %%a IN ("%thisdir%") DO SET p=%%~dpa
set parentdir=%p:~0,-1%

echo %thisdir%
echo %parentdir%

SET myIP=127.0.0.1

REM #------------------------------------#
REM #						             #
REM # 2. Created config replica set      #
REM #						             # 
REM #------------------------------------#
REM a little ping hack to wait 10 seconds, to ensure that the db at 36050 is accepting connections
ping -n 10 127.0.0.1 >nul
mongo.exe --port 36050 --shell %thisdir%\setup_config.js
REM #
REM #	
start /b mongos.exe --configdb "cfg/%myIP%:36050,%myIP%:36051,%myIP%:36052" --bind_ip %myIP%
REM #	
REM # 2.2. Remaining mongos processes listen to the explicit ports assigned by us
REM #	
start /b mongos.exe --configdb "cfg/%myIP%:36050,%myIP%:36051,%myIP%:36052" --port 36061 --bind_ip %myIP%
start /b mongos.exe --configdb "cfg/%myIP%:36050,%myIP%:36051,%myIP%:36052" --port 36062 --bind_ip %myIP%
start /b mongos.exe --configdb "cfg/%myIP%:36050,%myIP%:36051,%myIP%:36052" --port 36063 --bind_ip %myIP%

