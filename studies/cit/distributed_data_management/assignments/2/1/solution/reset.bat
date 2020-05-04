SET myIP=127.0.0.1

Taskkill /IM mongod.exe /F
Taskkill /IM mongos.exe /F

RD /S /Q cfg0
RD /S /Q cfg1
RD /S /Q cfg2

RD /S /Q london0
RD /S /Q london1
RD /S /Q london2
RD /S /Q amsterdam0
RD /S /Q amsterdam1
RD /S /Q amsterdam2
RD /S /Q newyork0
RD /S /Q newyork1
RD /S /Q newyork2
RD /S /Q newyork3
