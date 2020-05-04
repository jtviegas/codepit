connect sys/passw0rd as sysdba
alter session set "_ORACLE_SCRIPT"=true;


create user app identified by passw0rd default tablespace users temporary tablespace temp;
/
GRANT UNLIMITED TABLESPACE TO app;
/
CREATE OR REPLACE TYPE msg AS OBJECT (
    ts DATE
    , id number(9,0) 
    , content varchar2(256 char) 
);
/
GRANT EXECUTE on DBMS_AQ TO app;
GRANT EXECUTE on DBMS_AQADM TO app;
GRANT AQ_ADMINISTRATOR_ROLE TO app;
GRANT MGW_AGENT_ROLE TO app;



-- select link_name, link_type from MGW_LINKS;






                         
