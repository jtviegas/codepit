@echo off
setlocal EnableDelayedExpansion

SET p=%~dp0
SET thisdir=%p:~0,-1%
FOR %%a IN ("%thisdir%") DO SET p=%%~dpa
set parentdir=%p:~0,-1%

echo %thisdir%
echo %parentdir%

SET myIP=127.0.0.1
	
REM #-------------------------------------------------#
REM #						      #
REM # 3. Create the shards of our cluster	      #
REM #						      #
REM #-------------------------------------------------#
REM #	
REM # 3.1. Create the data directory for each of the replica sets servers
REM #	
mkdir london0
mkdir london1
mkdir london2
mkdir amsterdam0
mkdir amsterdam1
mkdir amsterdam2
mkdir newyork0
mkdir newyork1
mkdir newyork2
mkdir newyork3
REM #	
REM # 3.2. Start each member of the replica set 
REM #	
start /b mongod.exe --shardsvr --replSet london --dbpath london0 --port 37000 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet london --dbpath london1 --port 37001 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet london --dbpath london2 --port 37002 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet amsterdam --dbpath amsterdam0 --port 37100 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet amsterdam --dbpath amsterdam1 --port 37101 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet amsterdam --dbpath amsterdam2 --port 37102 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet newyork --dbpath newyork0 --port 37200 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet newyork --dbpath newyork1 --port 37201 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet newyork --dbpath newyork2 --port 37202 --bind_ip %myIP%
start /b mongod.exe --shardsvr --replSet newyork --dbpath newyork3 --port 37203 --bind_ip %myIP%

