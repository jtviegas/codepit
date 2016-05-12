/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aprestos.code.rdbms.utils;

/**
 *
 * @author jmv
 */
public interface Property 
{
	static final String rdbms_name="rdbms.name";
	static final String rdbms_host="rdbms.host";
	static final String rdbms_port="rdbms.port";
	static final String rdbms_ssl="rdbms.ssl";
	static final String rdbms_driver="rdbms.driver";
	static final String rdbms_protocol="rdbms.protocol";
	static final String rdbms_user="rdbms.user";
	static final String rdbms_password="rdbms.password";
	static final String rdbms_db="rdbms.db";
	static final String rdbms_db_schema="rdbms.db.schema";
	static final String rdbms_db_folder="rdbms.db.folder";
	static final String rdbms_db_create="rdbms.db.create"; //true or false
	static final String rdbms_db_shutdown_on_close="rdbms.db.shutdown.on.close"; //true or false
	
	static final String rdbms_derby_embedded_clean_shutdown_sql_exception_state=
		"rdbms.derby.embedded.clean.shutdown.sql.exception.state";
	static final String rdbms_derby_embedded_clean_shutdown_error_code=
		"rdbms.derby.embedded.clean.shutdown.error.code";
	static final String rdbms_derby_embedded_connection_shutdown_var=
		"rdbms.derby.embedded.connection.shutdown.var";
	static final String rdbms_derby_embedded_connection_create_var=
		"rdbms.derby.embedded.connection.create.var";
	
	
}
