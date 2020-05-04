@echo off
setlocal EnableDelayedExpansion

SET p=%~dp0
SET thisdir=%p:~0,-1%
FOR %%a IN ("%thisdir%") DO SET p=%%~dpa
set parentdir=%p:~0,-1%

echo %thisdir%
echo %parentdir%

SET myIP=127.0.0.1

REM #
REM #---------------------------------------------------#
REM #						        #
REM # 6. Shard the collection			        #
REM #						        #
REM #---------------------------------------------------#
REM #	
mongo.exe --shell %thisdir%\shard_collection.js
REM #



