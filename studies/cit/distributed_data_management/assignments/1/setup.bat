@echo off
setlocal EnableDelayedExpansion

SET p=%~dp0
SET thisdir=%p:~0,-1%
FOR %%a IN ("%thisdir%") DO SET p=%%~dpa
set parentdir=%p:~0,-1%

echo %thisdir%
echo %parentdir%

echo "going to import artists and artworks..."

rem include some file
rem call %thisdir%\VARS.cmd

set mongo_dir="C:\Program Files\MongoDB\Server\4.0\bin"
cd %mongo_dir%
mongoimport --db tate --collection artists --file %thisdir%\artists.json
mongoimport --db tate --collection artworks --file %thisdir%\artworks.json
cd %thisdir%

echo "...done."
