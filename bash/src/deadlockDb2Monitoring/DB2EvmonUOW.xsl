<?xml version="1.0" encoding="UTF-8"?>
<!-- ************************************************************************** -->
<!-- (c) Copyright IBM Corp. 2008 All rights reserved.                          -->
<!--                                                                            -->
<!-- The following sample of source code ("Sample") is owned by International   -->
<!-- Business Machines Corporation or one of its subsidiaries ("IBM") and is    -->
<!-- copyrighted and licensed, not sold. You may use, copy, modify, and         -->
<!-- distribute the Sample in any form without payment to IBM, for the purpose  -->
<!-- of assisting you in the development of your applications.                  -->
<!--                                                                            -->
<!-- The Sample code is provided to you on an "AS IS" basis, without warranty   -->
<!-- of any kind. IBM HEREBY EXPRESSLY DISCLAIMS ALL WARRANTIES, EITHER         -->
<!-- EXPRESS OR  IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES -->
<!-- OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. Some              -->
<!-- jurisdictions do not allow for the exclusion or limitation of implied      -->
<!-- warranties, so the above limitations or exclusions may not apply to you.   -->
<!-- IBM shall not be liable for any damages you suffer as a result of using,   -->
<!-- copying, modifying or distributing the Sample, even if IBM has been        -->
<!-- advised of the possibility of such damages.                                -->
<!-- ************************************************************************** -->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:um="http://www.ibm.com/xmlns/prod/db2/mon">

<xsl:output method="text"  indent="no"/>

<!-- ========================================================================== -->
<!-- Template   : Main                                                          -->
<!-- Description: Main template to process the entire XML document              -->
<!-- ========================================================================== -->
<xsl:template match="/">

<!-- ========================================================== -->
<!-- Print out each lock event in details                       -->
<!-- ========================================================== -->
  <xsl:for-each select="um:db2_uow_event">
    <xsl:apply-templates select="." />
  </xsl:for-each>
  <xsl:text>&#10;</xsl:text>
</xsl:template>


<!-- ========================================================================== -->
<!-- Template   : UOW event details                                             -->
<!-- Description: Template will process each db2UOWEvent node contained in the  -->
<!--              XML document and print out the event details.                 -->
<!-- ========================================================================== -->
<xsl:template match="um:db2_uow_event">

  <xsl:text>&#10;</xsl:text>
  <xsl:text>&#10;</xsl:text>

  <!-- ========================================================== -->
  <!-- Print out the Event details header                         -->
  <!-- ========================================================== -->
  <xsl:text>-------------------------------------------------------&#10;</xsl:text>
  <xsl:text>Event ID               : </xsl:text>
    <xsl:value-of select="@id" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Event Type             : </xsl:text>
    <xsl:value-of select="@type" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Event Timestamp        : </xsl:text>
    <xsl:value-of select="concat(substring(@timestamp, 1, 10),
                                 '-',
                                 substring(@timestamp, 12, 2),
                                 '.',
                                 substring(@timestamp, 15, 2),
                                 '.',
                                 substring(@timestamp, 18, 9))" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Member                 : </xsl:text>
    <xsl:value-of select="@member" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Release                : </xsl:text>
    <xsl:value-of select="@release" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>-------------------------------------------------------&#10;</xsl:text>

  <!-- ========================================================== -->
  <!-- Print out the database level details                       -->
  <!-- ========================================================== -->
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Database Level Details&#10;</xsl:text>
  <xsl:text>----------------------&#10;</xsl:text>
  <xsl:text>Database Member Activation Time  : </xsl:text>
    <xsl:value-of select="concat(substring(um:member_activation_time/text(), 1, 10),
                                 '-',
                                 substring(um:member_activation_time/text(), 12, 2),
                                 '.',
                                 substring(um:member_activation_time/text(), 15, 2),
                                 '.',
                                 substring(um:member_activation_time/text(), 18, 9))" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Coordinator Member               : </xsl:text>
    <xsl:value-of select="um:coord_member/text()" />
  <xsl:text>&#10;</xsl:text>

  <!-- ========================================================== -->
  <!-- Print out the connection level details                     -->
  <!-- ========================================================== -->
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Connection Level Details&#10;</xsl:text>
  <xsl:text>------------------------&#10;</xsl:text>
  <xsl:text>Application ID             : </xsl:text>
    <xsl:value-of select="um:application_id/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Application Handle         : </xsl:text>
    <xsl:value-of select="um:application_handle/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Application Name           : </xsl:text>
    <xsl:value-of select="um:application_name/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Session Authorization ID   : </xsl:text>
    <xsl:value-of select="um:session_authid/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>System Authorization ID    : </xsl:text>
    <xsl:value-of select="um:system_authid/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Connection Timestamp       : </xsl:text>
    <xsl:value-of select="concat(substring(um:connection_time/text(), 1, 10),
                                 '-',
                                 substring(um:connection_time/text(), 12, 2),
                                 '.',
                                 substring(um:connection_time/text(), 15, 2),
                                 '.',
                                 substring(um:connection_time/text(), 18, 9))" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Client Process ID          : </xsl:text>
    <xsl:value-of select="um:client_pid/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Client Platform            : </xsl:text>
    <xsl:value-of select="um:client_platform/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Client Product ID          : </xsl:text>
    <xsl:value-of select="um:client_product_id/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Client Protocol            : </xsl:text>
    <xsl:value-of select="um:client_protocol/text()" />
  <xsl:text>&#10;</xsl:text>

  <!-- ========================================================== -->
  <!-- Print out the UOW level details                            -->
  <!-- ========================================================== -->
  <xsl:text>&#10;</xsl:text>
  <xsl:text>UOW Level Details&#10;</xsl:text>
  <xsl:text>------------------------&#10;</xsl:text>
  <xsl:text>Start Time                 : </xsl:text>
    <xsl:value-of select="concat(substring(um:start_time/text(), 1, 10),
                                 '-',
                                 substring(um:start_time/text(), 12, 2),
                                 '.',
                                 substring(um:start_time/text(), 15, 2),
                                 '.',
                                 substring(um:start_time/text(), 18, 9))" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Stop Time                  : </xsl:text>
    <xsl:value-of select="concat(substring(um:stop_time/text(), 1, 10),
                                 '-',
                                 substring(um:stop_time/text(), 12, 2),
                                 '.',
                                 substring(um:stop_time/text(), 15, 2),
                                 '.',
                                 substring(um:stop_time/text(), 18, 9))" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Completion Status          : </xsl:text>
    <xsl:value-of select="um:completion_status/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>UOW ID                     : </xsl:text>
    <xsl:value-of select="um:uow_id/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Workoad Occurrence ID      : </xsl:text>
    <xsl:value-of select="um:workload_occurrence_id/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Workload Name              : </xsl:text>
    <xsl:value-of select="um:workload_name/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Workoad ID                 : </xsl:text>
    <xsl:value-of select="um:workload_id" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Service Superclass Name    : </xsl:text>
    <xsl:value-of select="um:service_superclass_name/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Service Subclass Name      : </xsl:text>
    <xsl:value-of select="um:service_subclass_name/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Service Class ID           : </xsl:text>
    <xsl:value-of select="um:service_class_id" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Tpmon Userid               : </xsl:text>
    <xsl:value-of select="um:client_userid/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Tpmon Workstation Name     : </xsl:text>
    <xsl:value-of select="um:client_wrkstnname/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Tpmon Application Name     : </xsl:text>
    <xsl:value-of select="um:client_applname/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Tpmon Accounting String    : </xsl:text>
    <xsl:value-of select="um:client_acctng/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Local Transaction ID       : </xsl:text>
    <xsl:value-of select="um:local_transaction_id/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>Global Transaction ID      : </xsl:text>
    <xsl:value-of select="um:global_transaction_id/text()" />
  <xsl:text>&#10;</xsl:text>

  <!-- ========================================================== -->
  <!-- Print out the UOW metrics details                          -->
  <!-- Metrics that are not part of the document, are             -->
  <!-- printed as empty strings                                   -->
  <!-- ========================================================== -->
  <xsl:text>&#10;</xsl:text>
  <xsl:text>UOW Metrics&#10;</xsl:text>
  <xsl:text>------------------------&#10;</xsl:text>
  <xsl:text>TOTAL_CPU_TIME                 : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:total_cpu_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TOTAL_WAIT_TIME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:total_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>ACT_ABORTED_TOTAL              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:act_aborted_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>ACT_COMPLETED_TOTAL            : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:act_completed_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>ACT_REJECTED_TOTAL             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:act_rejected_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AGENT_WAIT_TIME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:agent_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AGENT_WAITS_TOTAL              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:agent_waits_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>APP_RQSTS_COMPLETED_TOTAL      : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:app_rqsts_completed_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AUDIT_EVENTS_TOTAL             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:audit_events_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AUDIT_SUBSYSTEM_WAIT_TIME      : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:audit_subsystem_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AUDIT_SUBSYSTEM_WAITS_TOTAL    : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:audit_subsystem_waits_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AUDIT_FILE_WRITE_WAIT_TIME     : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:audit_file_write_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>AUDIT_FILE_WRITES_TOTAL        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:audit_file_writes_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_DATA_L_READS              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_data_l_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_INDEX_L_READS             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_index_l_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_TEMP_DATA_L_READS         : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_temp_data_l_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_TEMP_INDEX_L_READS        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_temp_index_l_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_TEMP_XDA_L_READS          : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_temp_xda_l_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_XDA_L_READS               : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_xda_l_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_DATA_P_READS              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_data_p_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_INDEX_P_READS             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_index_p_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_TEMP_DATA_P_READS         : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_temp_data_p_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_TEMP_INDEX_P_READS        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_temp_index_p_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_TEMP_XDA_P_READS          : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_temp_xda_p_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_XDA_P_READS               : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_xda_p_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_DATA_WRITES               : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_data_writes/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_INDEX_WRITES              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_index_writes/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_XDA_WRITES                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_xda_writes/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_READ_TIME                 : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_read_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>POOL_WRITE_TIME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:pool_write_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>CLIENT_IDLE_WAIT_TIME          : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:client_idle_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DEADLOCKS                      : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:deadlocks/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIAGLOG_WRITES_TOTAL           : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:diaglog_writes_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIAGLOG_WRITE_WAIT_TIME        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:diaglog_write_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIRECT_READS                   : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:direct_reads/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIRECT_READ_TIME               : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:direct_read_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIRECT_WRITES                  : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:direct_writes/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIRECT_WRITE_TIME              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:direct_write_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIRECT_READ_REQS               : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:direct_read_reqs/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>DIRECT_WRITE_REQS              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:direct_write_reqs/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_RECV_VOLUME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_recv_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_RECVS_TOTAL                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_recvs_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_SEND_VOLUME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_send_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_SENDS_TOTAL                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_sends_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_RECV_WAIT_TIME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_recv_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_SEND_WAIT_TIME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_send_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_MESSAGE_RECV_VOLUME        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_message_recv_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_MESSAGE_RECVS_TOTAL        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_message_recvs_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_MESSAGE_RECV_WAIT_TIME     : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_message_recv_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_MESSAGE_SEND_VOLUME        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_message_send_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_MESSAGE_SENDS_TOTAL        : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_message_sends_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_MESSAGE_SEND_WAIT_TIME     : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_message_send_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_TQ_RECV_WAIT_TIME          : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_tq_recv_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_TQ_RECVS_TOTAL             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_tq_recvs_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_TQ_RECV_VOLUME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_tq_recv_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_TQ_SEND_WAIT_TIME          : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_tq_send_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_TQ_SENDS_TOTAL             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_tq_sends_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>FCM_TQ_SEND_VOLUME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:fcm_tq_send_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TQ_TOT_SEND_SPILLS             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tq_tot_send_spills/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>IPC_RECV_VOLUME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:ipc_recv_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>IPC_RECV_WAIT_TIME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:ipc_recv_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>IPC_RECVS_TOTAL                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:ipc_recvs_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>IPC_SEND_VOLUME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:ipc_send_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>IPC_SEND_WAIT_TIME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:ipc_send_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>IPC_SENDS_TOTAL                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:ipc_sends_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOCK_ESCALS                    : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:lock_escals/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOCK_TIMEOUTS                  : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:lock_timeouts/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOCK_WAIT_TIME                 : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:lock_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOCK_WAITS                     : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:lock_waits/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOG_BUFFER_WAIT_TIME           : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:log_buffer_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>NUM_LOG_BUFFER_FULL            : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:num_log_buffer_full/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOG_DISK_WAIT_TIME             : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:log_disk_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>LOG_DISK_WAITS_TOTAL           : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:log_disk_waits_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>RQSTS_COMPLETED_TOTAL          : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:rqsts_completed_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>ROWS_MODIFIED                  : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:rows_modified/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>ROWS_READ                      : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:rows_read/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>ROWS_RETURNED                  : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:rows_returned/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TCPIP_RECV_VOLUME              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tcpip_recv_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TCPIP_SEND_VOLUME              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tcpip_send_volume/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TCPIP_RECV_WAIT_TIME           : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tcpip_recv_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TCPIP_RECVS_TOTAL              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tcpip_recvs_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TCPIP_SEND_WAIT_TIME           : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tcpip_send_wait_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TCPIP_SENDS_TOTAL              : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:tcpip_sends_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TOTAL_APP_RQST_TIME            : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:total_app_rqst_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>TOTAL_RQST_TIME                : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:total_rqst_time/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>WLM_QUEUE_TIME_TOTAL           : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:wlm_queue_time_total/text()" />
  <xsl:text>&#10;</xsl:text>
  <xsl:text>WLM_QUEUE_ASSIGNMENTS_TOTAL    : </xsl:text>
    <xsl:value-of select="um:system_metrics/um:wlm_queue_assignments_total/text()" />
  <xsl:text>&#10;</xsl:text>
</xsl:template>



</xsl:stylesheet>
