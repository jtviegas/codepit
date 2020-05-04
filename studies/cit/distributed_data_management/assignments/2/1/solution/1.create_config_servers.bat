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
REM #-------------------------------------------------#
REM #						      #
REM # 1. Start the config server database instances   #
REM #						      #
REM #-------------------------------------------------#
REM #	
REM # 1.1. Create the data directory for each of the config servers
REM #	
mkdir cfg0
mkdir cfg1
mkdir cfg2
REM #	
REM # 1.2. Start the config server instances 
REM #	
start /b mongod.exe --replSet cfg --configsvr --dbpath cfg0 --port 36050 --bind_ip %myIP%
start /b mongod.exe --replSet cfg --configsvr --dbpath cfg1 --port 36051 --bind_ip %myIP%
start /b mongod.exe --replSet cfg --configsvr --dbpath cfg2 --port 36052 --bind_ip %myIP%
