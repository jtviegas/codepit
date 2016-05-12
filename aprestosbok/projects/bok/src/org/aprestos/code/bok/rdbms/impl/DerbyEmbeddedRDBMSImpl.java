/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package org.aprestos.code.bok.rdbms.impl;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.aprestos.code.bok.common.misc.ExceptionUtils;
import org.aprestos.code.bok.logger.LoggerFactory;
import org.aprestos.code.bok.logger.interfaces.LoggerInterface;
import org.aprestos.code.bok.rdbms.exceptions.DDLOperationException;
import org.aprestos.code.bok.rdbms.exceptions.RDBMSConnectionException;
import org.aprestos.code.bok.rdbms.exceptions.RDBMSException;
import org.aprestos.code.bok.rdbms.exceptions.SchemaLoadException;
import org.aprestos.code.bok.rdbms.interfaces.RDBMSInterface;
import org.aprestos.code.bok.rdbms.utils.Property;
import org.xml.sax.InputSource;



/**
 * {@link RDBMSInterface} implementation for the 
 * <a href="http://db.apache.org/derby/">Derby</a> RDBMS.
 * @author jmv
 */
public class DerbyEmbeddedRDBMSImpl implements RDBMSInterface
{
	private static LoggerInterface logger = LoggerFactory.getInstance()
			.getLogger(DerbyEmbeddedRDBMSImpl.class);

	private static final String CLEAN_SHUTDOWN_SQL_EXCEPTION_STATE = "XJ015";
	private static final int CLEAN_SHUTDOWN_ERROR_CODE = 50000;
	private static final String CONNECTION_SHUTDOWN_VAR = "shutdown";
	private static final String CONNECTION_CREATE_VAR = "create";

	private Properties properties;
	private Connection connection;

	public DerbyEmbeddedRDBMSImpl(Properties properties) throws SQLException, RDBMSConnectionException, 
					DDLOperationException, SchemaLoadException, RDBMSException
	{
		logger.enter("DerbyEmbeddedRDBMSImpl", new Object[]{ properties });
		this.properties = properties;
		init();
		logger.leave("DerbyEmbeddedRDBMSImpl");
	}

	public Connection getConnection()
	{
		logger.enter("getConnection", new Object[]
			{ connection });
		logger.leave("getConnection", connection);
		return connection;
	}

	public boolean isConnectionOpen()
	{
		logger.enter("isConnectionOpen");
		boolean result = false;
		
		try
		{
			if (null != connection)
			{
				if (!connection.isClosed())
				{
					logger.debug("derby embedded connection is open.");
					result = true;
				}
				else
					logger.debug("derby embedded connection is closed");
			}
		}
		catch (Exception x)
		{
			logger.error(ExceptionUtils.stackTrace2String(x),x);
		}
		
		logger.leave("isConnectionOpen", result);
		return result;
	}

	public void openConnection() throws RDBMSConnectionException
	{
		logger.enter("openConnection");
		try
		{
			if (isConnectionOpen())
				return;
			
			// load database driver
			String driver = this.properties.getProperty(Property.driver.getKey());
			Class.forName(driver).newInstance();

			Properties props = new Properties();
			props.put("user", this.properties.getProperty(Property.user
					.getKey()));
			props.put("password", this.properties.getProperty(Property.password
					.getKey()));

			connection = DriverManager.getConnection(getConnectionString2open(), props);
			logger.debug("derby embedded connection has been opened");

		}
		catch (Exception x)
		{
			throw new RDBMSConnectionException(x);
		}
		logger.leave("openConnection");
	}

	public void closeConnection() throws SQLException
	{
		logger.enter("closeConnection");
		closeRDBMSConnection();
		logger.debug("derby embedded connection has been closed");
		logger.leave("closeConnection");
	}

	/**
	 * Initializes the database based on the properties supplied.
	 * More specifically, checks for the presence of the database
	 * files and, if not, creates a new database based on the schema
	 * definition found in the properties.
	 * 
	 * @throws SQLException
	 * @throws RDBMSConnectionException
	 * @throws DDLOperationException
	 * @throws SchemaLoadException
	 * @throws RDBMSException
	 */
	private void init() throws SQLException, RDBMSConnectionException, DDLOperationException, 
				SchemaLoadException, RDBMSException
	{
		logger.enterIn(" init");
		
		if (!existsDatabase())
			createDatabase();
		
		logger.leaveIn("init");
	}

	private boolean existsDatabase() throws RDBMSException
	{
		logger.enterIn("existsDatabase");
		boolean result = false;

		String dbLocation = getURL();
		File file = new File(dbLocation);
		
		try
		{
			if (file.exists())
			{
				logger.debug("derby embedded db file exists.");
				result = true;
			}
			else
				logger.debug("derby embedded db file does not exist.");
		}
		catch(SecurityException se)
		{
			throw new RDBMSException(se);
		}
		
		logger.leaveIn("existsDatabase", result);
		return result;
	}

	private String getURL()
	{
		logger.enterIn("getURL");
		
		String result = properties.getProperty(Property.derby_embedded_rdbms_folder.getKey())
				+ System.getProperty("file.separator")
				+ properties.getProperty(Property.db_name.getKey());
		
		logger.leaveIn("getURL", result);
		return result;
	}

	private String getConnectionString2open()
	{
	    logger.enterIn("getConnectionString2open");

		String result = properties.getProperty(Property.protocol.getKey())
		+ getURL()
		+ ";" + CONNECTION_CREATE_VAR + "=" + 
		properties.getProperty(Property.derby_embedded_vars_create.getKey());
		
		logger.leaveIn("getConnectionString2open", result);
		return result; 
	}

	private void createDatabase() throws SQLException, RDBMSConnectionException, 
				SchemaLoadException, DDLOperationException
	{
		logger.enterIn("createDatabase");

		openConnection();

		createTables();

		closeConnection();

		logger.leaveIn("createDatabase");
	}

	private void createTables() throws SchemaLoadException, DDLOperationException
	{
		logger.enterIn(" createTables");
		Database db = null;
		
		try
		{
			InputStream schemaStream = this.getClass().getResourceAsStream(
					this.properties.getProperty(Property.db_schema.getKey()));
			InputSource is = new InputSource(schemaStream);
			db = new DatabaseIO().read(is);
			logger.debug("the db schema has been read.");
		}
		catch(Exception x)
		{
			throw new SchemaLoadException(x);
		}
		
		try
		{
		    org.apache.derby.jdbc.EmbeddedDataSource datasource = 
			new org.apache.derby.jdbc.EmbeddedDataSource();
			//datasource.setDatabaseName(this.properties.getProperty(Property.db_name.getKey()));
			datasource.setDatabaseName(getURL());
			Platform platform = PlatformFactory.createNewPlatformInstance(
					datasource);
			logger.debug("going to create the schema in the db...");
			platform.createTables(db, true, true);
			logger.debug("...schema created.");
		}
		catch(Exception x)
		{
			throw new DDLOperationException(x);
		}
		
		logger.leaveIn("createTables");
	}

	

	@SuppressWarnings("unused")
	private String getConnectionString2close()
	{
	    logger.enterIn("getConnectionString2close");

		String result = properties.getProperty(Property.protocol.getKey())
		+ getURL()
		+ ";" + CONNECTION_SHUTDOWN_VAR + "=" + 
		properties.getProperty(Property.derby_embedded_vars_shutdown.getKey());
		
		logger.leaveIn("getConnectionString2close", result);
		return result; 
	}
	
	private String getConnectionString2stopRDBMS()
	{
	    logger.enterIn("getConnectionString2stopRDBMS");

		String result = properties.getProperty(Property.protocol.getKey())
		+  ";" + CONNECTION_SHUTDOWN_VAR + "=" + 
		properties.getProperty(Property.derby_embedded_vars_shutdown.getKey());
		
		logger.leaveIn("getConnectionString2stopRDBMS", result);
		return result; 
	}
	
	/**
	 * TO BE TESTED
	 * @throws SQLException
	 */
	
	private void closeRDBMSConnection() throws SQLException
	{
		logger.enterIn("closeRDBMSConnection");
		try
		{
			DriverManager.getConnection(getConnectionString2stopRDBMS());
		}
		catch (SQLException x)
		{
			if (x.getErrorCode() == CLEAN_SHUTDOWN_ERROR_CODE
					&& x.getSQLState().equals(
							CLEAN_SHUTDOWN_SQL_EXCEPTION_STATE))
			{
				// we got the expected exception
				logger.debug("derby embedded has shut down normally");
				// Note that for single database shutdown, the expected
				// SQL state is "08006", and the error code is 45000.
			}
			else
			{
				// if the error code or SQLState is different, we have
				// an unexpected exception (shutdown failed)
				logger.debug("derby embedded has not shut down normally", x);
				throw x;
			}
		}

		logger.leaveIn("closeRDBMSConnection");
	}
	


}
