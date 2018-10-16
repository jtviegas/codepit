connect sys/passw0rd as sysdba
alter session set "_ORACLE_SCRIPT"=true;
drop user app;
create user app identified by passw0rd default tablespace users temporary tablespace temp;

CREATE OR REPLACE TYPE msg AS OBJECT (
    ts DATE
    , id number(9,0) 
    , content varchar2(256 char) 
);

DBMS_STREAMS_ADM.SET_UP_QUEUE(
   queue_table     IN  VARCHAR2  DEFAULT 'streams_queue_table',
   storage_clause  IN  VARCHAR2  DEFAULT NULL,
   queue_name      IN  VARCHAR2  DEFAULT 'streams_queue',
   queue_user      IN  VARCHAR2  DEFAULT NULL,
   comment         IN  VARCHAR2  DEFAULT NULL);