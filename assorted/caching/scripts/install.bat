@echo off

start %SCRIPTS%\start_mongodb.bat

sleep 6

:GET_CMD_FORK_PID
FOR /F %%T IN ('Wmic process where^(Name^="mongod.exe"^)get PArentProcessId^|more +1') DO (
SET /A cmdPid=%%T) &GOTO GET_MONGO_PID 

:GET_MONGO_PID
FOR /F %%T IN ('Wmic process where^(Name^="mongod.exe"^)get ProcessId^|more +1') DO (
SET /A mongoPid=%%T) &GOTO CALL_MVN 

:CALL_MVN
call mvn install
echo "################ killing mongo pid"
taskkill /F /PID %mongoPid%
echo "################ killing cmd pid"
taskkill /F /PID %cmdPid%