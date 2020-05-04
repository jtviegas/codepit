connect sys/passw0rd as sysdba
alter session set "_ORACLE_SCRIPT"=true;

drop procedure quser.enq_proc;

begin
   dbms_mgwadm.remove_job( job_name => 'mgw_ibmmq_job');
end;
/
begin
   DBMS_MGWADM.UNREGISTER_FOREIGN_QUEUE(name => 'ibmmq',linkname => 'ibmmq_link');
end;
/
begin
   dbms_mgwadm.remove_msgsystem_link( linkname => 'ibmmq_link');
end;
/
begin
   dbms_aqadm.stop_queue (queue_name => 'quser.msg_queue', wait => FALSE);
end;
/
begin
   dbms_aqadm.drop_queue(queue_name => 'quser.msg_queue');
end;
/
begin
   dbms_aqadm.drop_queue_table( queue_table => 'quser.msg_queue_table');
end;
/
begin
      dbms_mgwadm.shutdown;
end;
/

drop user quser cascade;
