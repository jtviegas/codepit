package org.tests.dumpread.data.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tests.dumpread.data.interfaces.DBConnection;
import org.tests.dumpread.exceptions.DBConnectionException;

@Component("dbconnection")
public class DBConnectionImpl implements DBConnection 
{
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionImpl.class);
	private Connection connection;
	
	@Value("${jdbc.driverClassName}")
	private String driver;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	
	
	public DBConnectionImpl()
	{
		
	}

	/* (non-Javadoc)
	 * @see org.tests.dumpread.connections.DBConnection#getConnection()
	 */
	public Connection getConnection() 
	{
		logger.debug("@getConnection");
		logger.debug("getConnection@", connection);
		return connection;
	}

	/* (non-Javadoc)
	 * @see org.tests.dumpread.connections.DBConnection#open()
	 */
	public void open() throws DBConnectionException
	{
		logger.debug("@open");
		try
		{
			if (null == this.connection || !isOpen())
			{
				// load database driver
				Class.forName(this.driver).newInstance();
				
				//load connection
				this.connection = DriverManager.getConnection(this.url + ";user="  + this.username +  ";password=" + this.password);
				
				logger.debug("derby embedded connection has been opened");
			}
		}
		catch (Exception x)
		{
			throw new DBConnectionException(x);
		}
		logger.debug("open@");
		
	}
	
	public void close() throws DBConnectionException 
	{
		logger.debug("@close");
		try 
		{
			if(null != this.connection)
				this.connection.close();
			
			this.connection = null;
		} 
		catch (SQLException e) 
		{
			throw new DBConnectionException(e);
		}
		logger.debug("close@");
	}

	private boolean isOpen()
	{
		logger.debug("@isOpen");
		
		boolean _result = false;

		try
		{
			if (null != connection)
			{
				if (!connection.isClosed())
				{
					logger.debug("derby embedded connection is open.");
					_result = true;
				}
				else
					logger.debug("derby embedded connection is closed");
			}
		}
		catch (Exception x)
		{
			//something is wrong
			logger.error(ExceptionUtils.getFullStackTrace(x));
		}

		logger.debug("isOpen@", _result);
		return _result;
	}



}
