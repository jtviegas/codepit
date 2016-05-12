/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package org.aprestos.code.rdbms.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.misc.ExceptionUtils;
import org.aprestos.code.rdbms.exceptions.RDBMSConnectionException;
import org.aprestos.code.rdbms.exceptions.RDBMSException;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;
import org.aprestos.code.rdbms.utils.Property;




/**
 * {@link RDBMSInterface} implementation for the 
 * <a href="http://db.apache.org/derby/">Derby</a> RDBMS.
 * @author jmv
 */
public class PostgresqlRDBMSImpl implements RDBMSInterface
{
	private static LoggerInterface logger = LoggerFactory.getInstance()
			.getLogger(PostgresqlRDBMSImpl.class);
	

	private Properties properties;
	private Connection connection;

	private static final String PROPS_SUFFIX = ".properties" ;
	
	public PostgresqlRDBMSImpl(Properties properties) throws RDBMSException
	{
		logger.enter("PostgresqlRDBMSImpl", new Object[]{ properties });
		init(properties);
		logger.leave("PostgresqlRDBMSImpl");
	}

	private void init(Properties props) throws RDBMSException
	{
		logger.enterIn("init", new Object[]{ props });
		try
		{
			String[] classname = PostgresqlRDBMSImpl.class.getName().split("\\.");
			logger.debug("going to get input from file " + classname[classname.length-1] + PROPS_SUFFIX);
			InputStream is = PostgresqlRDBMSImpl.class.getResourceAsStream(classname[classname.length-1] + PROPS_SUFFIX);
			this.properties = new Properties();
			this.properties.load(is);
		    logger.debug("loaded " + classname[classname.length-1] + PROPS_SUFFIX + " file");
			is.close();
			is=null;
			
			//consolidate properties in one place
			this.properties.put("user", props.getProperty(Property.rdbms_user));
			this.properties.put("password", props.getProperty(Property.rdbms_password));
			
			if(null != props.getProperty(Property.rdbms_ssl) &&
					props.getProperty(Property.rdbms_ssl).length() > 0)
				properties.put("ssl", props.getProperty(Property.rdbms_ssl));
			
			this.properties.put(Property.rdbms_host, props.getProperty(Property.rdbms_host));
			this.properties.put(Property.rdbms_db, props.getProperty(Property.rdbms_db));
			this.properties.put(Property.rdbms_port, props.getProperty(Property.rdbms_port));	
		}
		catch(Exception ex)
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

	public void openConnection() throws RDBMSConnectionException
	{
		logger.enter("openConnection");
		try
		{
			if (isConnectionOpen())
				return;
    		
			// load database driver
			String driver = this.properties.getProperty(Property.rdbms_driver);
			Class.forName(driver).newInstance();

			connection = DriverManager.getConnection(getURL(), properties);
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
		
		if(null != connection)
			connection.close();
		
		logger.leave("closeConnection");
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
		
		logger.leave("isConnectionOpen", new Boolean(result));
		return result;
	}

	private String getURL()
	{
		logger.enterIn("getURL");
		
		String result = properties.getProperty(Property.rdbms_protocol) + "//" 
				+ properties.getProperty(Property.rdbms_host) + ":" 
				+ properties.getProperty(Property.rdbms_port) + "/"
				+ properties.getProperty(Property.rdbms_db)	;
		
		
		logger.leaveIn("getURL", result);
		return result;
	}


}
