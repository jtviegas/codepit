@echo off

call %~dp0%/environment.bat
start %MONGOD% --dbpath %DB_PATH% --pidfilepath %MONGOPIDFILE%
sleep 20
rem TODO the other engines