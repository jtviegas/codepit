@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

set this_script=%~f0
for %%F in ("%this_script%") do set script_folder=%%~dpF
set pwd=%cd%

if [%1]==[] goto usage
if [%2]==[] goto usage

call certUtil -hashfile %2 %1
goto :eof
:usage
echo "Usage: %0 <MD5|SHA256|...> <file>"
exit /B 1