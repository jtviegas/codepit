@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

set this_script=%~f0
for %%F in ("%this_script%") do set script_folder=%%~dpF
set pwd=%cd%

if [%1]==[] goto usage
if [%2]==[] goto usage

call mvn archetype:generate -B -DarchetypeGroupId=pl.org.miki -DarchetypeArtifactId=java8-quickstart-archetype -DarchetypeVersion=1.0.0 -DgroupId=%1 -DartifactId=%2 -Dversion=0.1 -Dpackage=%1.%2

goto :eof
:usage
echo "Usage: %0 <groupId> <artifactId>"
exit /B 1