package org.aprestos.code.rdbms.postgresql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.rdbms.AbstractRDBMSFactory;
import org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class PostgresqlRDBMSTests extends TestCase
{
	private Properties props;
	private RDBMSFactoryInterface f;
	private RDBMSInterface o;
	
	private static LoggerInterface logger;
	
	public static Test suite()
	{
		return new TestSuite(PostgresqlRDBMSTests.class);
	}
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		InputStream is = this.getClass().getResourceAsStream("PostgresqlRDBMSTests.properties");
		props = new Properties();
		props.load(is);
		is.close();
		is=null;
		LoggerFactory.initialize(props);
		logger = LoggerFactory.getInstance().getLogger(PostgresqlRDBMSTests.class);
		f = AbstractRDBMSFactory.getFactory(props);
		
		f.initialize();
		o = f.getRDBMS();
		
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		props = null;
		f=null;
		o=null;
	}

	public void testConnectionClosed()
	{
		try
		{
			assertFalse(o.isConnectionOpen());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void testConnectionOpen()
	{
		try
		{
			o.openConnection();
			
			assertTrue(o.isConnectionOpen());
			
			o.closeConnection();
			
			assertFalse(o.isConnectionOpen());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void testGetResultSet()
	{
		try
		{
			o.openConnection();
			
			assertTrue(o.isConnectionOpen());
		
			Connection c = o.getConnection();
			
			Statement s = c.createStatement();
			
			ResultSet r = 	s.executeQuery("select seq from businessdate");
			r.next();
			int seq = r.getInt(1);
			logger.debug("first sequence is " + seq);
			o.closeConnection();
			assertFalse(o.isConnectionOpen());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}