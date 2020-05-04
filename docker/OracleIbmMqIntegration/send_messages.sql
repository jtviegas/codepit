connect sys/passw0rd as sysdba

CREATE or Replace  PROCEDURE quser.enq_proc (payload RAW)  IS 
  enqopt     DBMS_AQ.ENQUEUE_OPTIONS_T;
  mprop      DBMS_AQ.MESSAGE_PROPERTIES_T;
  enq_msgid  RAW(16);
BEGIN
  mprop.SENDER_ID := SYS.AQ$_AGENT('default_agent', NULL, NULL); 
  DBMS_AQ.ENQUEUE(
    queue_name          =>  'quser.msg_queue',
    enqueue_options     =>  enqopt,
    message_properties  =>  mprop,
    payload             =>  payload,
    msgid               =>  enq_msgid);
END;
/

DECLARE
  payload varchar2(256);
BEGIN
    payload := '{"ts":"12/Dec/12", "id": 129456, "text": "bladsadsdas"}'; 
  	quser.enq_proc(UTL_RAW.CAST_TO_RAW(payload));
END;
/
COMMIT;
/