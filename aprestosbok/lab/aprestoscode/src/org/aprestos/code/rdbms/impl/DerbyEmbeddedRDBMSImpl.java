/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package org.aprestos.code.rdbms.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.misc.ExceptionUtils;
import org.aprestos.code.rdbms.exceptions.DDLOperationException;
import org.aprestos.code.rdbms.exceptions.RDBMSConnectionException;
import org.aprestos.code.rdbms.exceptions.RDBMSException;
import org.aprestos.code.rdbms.exceptions.SchemaLoadException;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;
import org.aprestos.code.rdbms.utils.Property;
import org.xml.sax.InputSource;


/**
 * {@link RDBMSInterface} implementation for the <a
 * href="http://db.apache.org/derby/">Derby</a> RDBMS.
 * 
 * @author jmv
 */
public class DerbyEmbeddedRDBMSImpl implements RDBMSInterface
{
	private static LoggerInterface logger = LoggerFactory.getInstance()
			.getLogger(DerbyEmbeddedRDBMSImpl.class);
	private static final String PROPS_SUFFIX = ".properties";
	private static final String TRUE = "true";
	private static final String FALSE = "false";

	private Properties implProps;
	private Connection connection;

	public DerbyEmbeddedRDBMSImpl(Properties properties) throws RDBMSException
	{
		logger.enter("DerbyEmbeddedRDBMSImpl", new Object[]
			{ properties });
		init(properties);
		logger.leave("DerbyEmbeddedRDBMSImpl");
	}

	/**
	 * Initializes the database based on the properties supplied. More
	 * specifically, checks for the presence of the database files and, if not,
	 * creates a new database based on the schema definition found in the
	 * properties.
	 * 
	 * @throws SQLException
	 * @throws RDBMSConnectionException
	 * @throws DDLOperationException
	 * @throws SchemaLoadException
	 * @throws RDBMSException
	 */
	private void init(Properties userProps) throws RDBMSException
	{
		logger.enterIn(" init");
		try
		{
			String[] classname = DerbyEmbeddedRDBMSImpl.class.getName().split(
					"\\.");
			logger.debug("going to get input from file "
					+ classname[classname.length - 1] + PROPS_SUFFIX);
			InputStream is = DerbyEmbeddedRDBMSImpl.class
					.getResourceAsStream(classname[classname.length - 1]
							+ PROPS_SUFFIX);
			implProps = new Properties();
			implProps.load(is);
			logger.debug("loaded " + classname[classname.length - 1]
					+ PROPS_SUFFIX + " file");
			is.close();
			is = null;

			// consolidate properties in one place
			// properties.put(Property.rdbms_driver,
			// props.getProperty(Property.rdbms_driver));
			implProps.put("user", userProps.getProperty(Property.rdbms_user));
			implProps.put("password", userProps
					.getProperty(Property.rdbms_password));
			implProps.put(Property.rdbms_db, userProps
					.getProperty(Property.rdbms_db));
			implProps.put(Property.rdbms_db_folder, userProps
					.getProperty(Property.rdbms_db_folder));
			implProps.put(Property.rdbms_db_create, userProps
					.getProperty(Property.rdbms_db_create));
			implProps.put(Property.rdbms_db_schema, userProps
					.getProperty(Property.rdbms_db_schema));
			implProps.put(Property.rdbms_db_shutdown_on_close, userProps
					.getProperty(Property.rdbms_db_shutdown_on_close));

			if (!existsDatabase())
				createDatabase();
		}
		catch (Exception ex)
		{
			throw new RDBMSException(ex);
		}
		logger.leaveIn("init");
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
			logger.error(ExceptionUtils.stackTrace2String(x), x);
		}

		logger.leave("isConnectionOpen", new Boolean(result));
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
			Class.forName(this.implProps.getProperty(Property.rdbms_driver))
					.newInstance();

			connection = DriverManager.getConnection(
					getConnectionString2open(), this.implProps);
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
		catch (SecurityException se)
		{
			throw new RDBMSException(se);
		}

		logger.leaveIn("existsDatabase", new Boolean(result));
		return result;
	}

	private String getURL()
	{
		logger.enterIn("getURL");

		String result = this.implProps.getProperty(Property.rdbms_db_folder)
				+ System.getProperty("file.separator")
				+ this.implProps.getProperty(Property.rdbms_db);

		logger.leaveIn("getURL", result);
		return result;
	}

	private String getConnectionString2open()
	{
		logger.enterIn("getConnectionString2open");

		String creation = FALSE;
		if (null != this.implProps.getProperty(Property.rdbms_db_create)
				&& TRUE.equals(this.implProps
						.getProperty(Property.rdbms_db_create)))
		{
			creation = TRUE;
		}

		String result = this.implProps.getProperty(Property.rdbms_protocol)
				+ getURL()
				+ ";"
				+ this.implProps
						.getProperty(Property.rdbms_derby_embedded_connection_create_var)
				+ "=" + creation;

		logger.leaveIn("getConnectionString2open", result);
		return result;
	}

	private void createDatabase()	throws SQLException,
									RDBMSConnectionException,
									SchemaLoadException,
									DDLOperationException
	{
		logger.enterIn("createDatabase");

		openConnection();

		createTables();

		closeConnection();

		logger.leaveIn("createDatabase");
	}

	private void createTables()	throws SchemaLoadException,
								DDLOperationException
	{
		logger.enterIn("  createTables");
		Database db = null;

		try
		{
//			InputStream schemaStream = this.getClass().getResourceAsStream(
//					this.implProps.getProperty(Property.rdbms_db_schema));
			InputStream schemaStream = new FileInputStream(
					this.implProps.getProperty(Property.rdbms_db_schema));
			InputSource schema = new InputSource(schemaStream);
			// File schema = new
			// File(this.implProps.getProperty(Property.rdbms_db_schema));
			db = new DatabaseIO().read(schema);

			logger.debug("the db schema has been read.");
		}
		catch (Exception x)
		{
			throw new SchemaLoadException(x);
		}

		try
		{
			org.apache.derby.jdbc.EmbeddedDataSource datasource = new org.apache.derby.jdbc.EmbeddedDataSource();
			// datasource.setDatabaseName(this.properties.getProperty(Property.
			// db_name.getKey()));
			datasource.setDatabaseName(getURL());
			Platform platform = PlatformFactory
					.createNewPlatformInstance(datasource);
			logger.debug("going to create the schema in the db...");
			platform.createTables(db, true, true);
			logger.debug("...schema created.");
		}
		catch (Exception x)
		{
			throw new DDLOperationException(x);
		}

		logger.leaveIn("createTables");
	}

	private String getConnectionString2close()
	{
		logger.enterIn("getConnectionString2close");

		String shutdown = "false";
		if (null != this.implProps
				.getProperty(Property.rdbms_db_shutdown_on_close)
				&& TRUE.equals(this.implProps
						.getProperty(Property.rdbms_db_shutdown_on_close)))
		{
			shutdown = TRUE;
		}

		String result = this.implProps.getProperty(Property.rdbms_protocol)
				+ getURL()
				+ ";"
				+ this.implProps
						.getProperty(Property.rdbms_derby_embedded_connection_shutdown_var)
				+ "=" + shutdown;

		logger.leaveIn("getConnectionString2close", result);
		return result;
	}

	private String getConnectionString2stopRDBMS()
	{
		logger.enterIn("getConnectionString2stopRDBMS");

		String shutdown = "false";
		if (null != this.implProps
				.getProperty(Property.rdbms_db_shutdown_on_close)
				&& TRUE.equals(this.implProps
						.getProperty(Property.rdbms_db_shutdown_on_close)))
		{
			shutdown = TRUE;
		}

		String result = this.implProps.getProperty(Property.rdbms_protocol)
				+ ";"
				+ this.implProps
						.getProperty(Property.rdbms_derby_embedded_connection_shutdown_var)
				+ "=" + shutdown;

		logger.leaveIn("getConnectionString2stopRDBMS", result);
		return result;
	}

	/**
	 * TO BE TESTED
	 * 
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

			if (x.getErrorCode() == Integer
					.parseInt(this.implProps
							.getProperty(Property.rdbms_derby_embedded_clean_shutdown_error_code))
					&& x
							.getSQLState()
							.equals(
									this.implProps
											.getProperty(Property.rdbms_derby_embedded_clean_shutdown_sql_exception_state)))
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
