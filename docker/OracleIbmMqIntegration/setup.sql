connect sys/passw0rd as sysdba
alter session set "_ORACLE_SCRIPT"=true;


create user quser identified by passw0rd default tablespace users temporary tablespace temp;
GRANT UNLIMITED TABLESPACE TO quser;
GRANT CREATE SESSION to quser;
GRANT EXECUTE on DBMS_AQ TO quser;
GRANT EXECUTE on DBMS_AQADM TO quser;
GRANT AQ_ADMINISTRATOR_ROLE TO quser;
GRANT CONNECT, MGW_AGENT_ROLE TO quser;
GRANT MGW_ADMINISTRATOR_ROLE to quser;
GRANT EXECUTE ON DBMS_STREAMS_ADM TO quser;

begin
    dbms_mgwadm.db_connect_info(
      username => 'quser',
      password => 'passw0rd',
      database => 'oracledb');
      dbms_mgwadm.startup;
end;
/
-- select AGENT_STATUS,AGENT_PING,LAST_ERROR_MSG from MGW_GATEWAY;

begin
    
    dbms_aqadm.create_queue_table( queue_table => 'quser.msg_queue_table', queue_payload_type =>'RAW');
    dbms_aqadm.create_queue(queue_name => 'quser.msg_queue', queue_table => 'quser.msg_queue_table');
end;
/

begin
   dbms_aqadm.start_queue (queue_name => 'quser.msg_queue');
end;
/

-- now that AQ queue is created and started, create the messaging link to the WebSphere MQ provider
declare
   v_mgw_props sys.mgw_properties;
   v_mq_props sys.mgw_mqseries_properties;
begin
    v_mq_props := sys.mgw_mqseries_properties.construct(); 
    v_mq_props.max_connections := 5;
    v_mq_props.interface_type := dbms_mgwadm.mqseries_base_java_interface;
    v_mq_props.username := 'app';
    v_mq_props.password := NULL;
    v_mq_props.hostname := 'ibmmq';
    v_mq_props.port     := 1414;
    v_mq_props.channel  := 'DEV.APP.SVRCONN';
    v_mq_props.queue_manager := 'ibmmq'; 
    v_mq_props.outbound_log_queue := 'DEV.QUEUE.2';
    v_mq_props.inbound_log_queue :=  'DEV.QUEUE.3';
    
   dbms_mgwadm.create_msgsystem_link(
      linkname => 'ibmmq_link', 
      properties => v_mq_props, 
      options => v_mgw_props,
      agent_name=>'default_agent',
      comment => 'link to connect to IBM MQ' );
end;
/

begin
   DBMS_MGWADM.REGISTER_FOREIGN_QUEUE( name => 'ibmmq',linkname => 'ibmmq_link', provider_queue => 'DEV.QUEUE.1', domain => DBMS_MGWADM.DOMAIN_QUEUE);  
end;
/

begin
   dbms_mgwadm.create_job(
      job_name => 'mgw_ibmmq_job',
      propagation_type => dbms_mgwadm.outbound_propagation,
      source => 'quser.msg_queue',
      destination => 'ibmmq@ibmmq_link',
      enabled => TRUE,
      poll_interval => 10,comments => 'my test ibmmq Job.');
end;
/




