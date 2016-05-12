package org.aprestos.code.wn.sap.idoc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.rdbms.AbstractRDBMSFactory;
import org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;
import org.aprestos.code.wn.sap.idoc.connectors.IDocConnectorFactory;
import org.aprestos.code.wn.sap.idoc.connectors.interfaces.IDocConnectorInterface;
import org.aprestos.code.wn.sap.idoc.connectors.tplinux.TPLinuxIDocConnector;
import org.aprestos.code.wn.sap.idoc.impl.SegmentsCache;
import org.aprestos.code.wn.sap.idoc.interfaces.IStructure;
import org.aprestos.code.wn.sap.idoc.interfaces.ISegment;


public class DataConnectorTests extends TestCase
{
	private RDBMSInterface o;
	private static LoggerInterface logger;

	public void testGetStructure()
	{
		try
		{
			IDocConnectorInterface c = IDocConnectorFactory.getDataconnector("connection");
			o.openConnection();
			c.setSource(o.getConnection());
			c.setArgs(new String[]{"2009060400"});
			IStructure[] _s = c.getIDocs("wpuksr01");
			assertNotNull(_s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AssertionFailedError(e.getMessage());
		}
	}
	
	public void testSegmentsCache()
	{
		try
		{
			ISegment _s = SegmentsCache.getSegment("e1wpk01");
			assertNotNull(_s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AssertionFailedError(e.getMessage());
		}
		
	}
	
	public void testGetTPlinuxDataConnector()
	{
		
		try
		{
			IDocConnectorInterface c = IDocConnectorFactory.getDataconnector("connection");
			
			
			assertEquals(TPLinuxIDocConnector.class.getName(), c.getClass().getName());
			
			o.openConnection();
			
			c.setSource(o.getConnection());
			
			assertNotNull(c);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AssertionFailedError(e.getMessage());
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

	/*
	 *  ---------------------------------------------------------------
	 *  					Boiler Plate code
	 *  ---------------------------------------------------------------
	 */
	public static Test suite()
	{
		return new TestSuite(DataConnectorTests.class);
	}
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		InputStream is = this.getClass().getResourceAsStream("DataConnectorTests.properties");
		Properties props = new Properties();
		props.load(is);
		is.close();
		is = null;
		LoggerFactory.initialize(props);
		logger = LoggerFactory.getInstance().getLogger(DataConnectorTests.class);
		RDBMSFactoryInterface f = AbstractRDBMSFactory.getFactory(props);
		
		f.initialize();
		o = f.getRDBMS();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		o=null;
	}

}