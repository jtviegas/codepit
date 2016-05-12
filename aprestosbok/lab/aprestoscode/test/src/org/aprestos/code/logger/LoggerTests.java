package org.aprestos.code.logger;

import java.io.InputStream;
import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerFactoryInterface;
import org.aprestos.code.logger.interfaces.LoggerInterface;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class LoggerTests extends TestCase
{
	private LoggerInterface o = null;
	
	public static Test suite()
	{
		return new TestSuite(LoggerTests.class);
	}
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		InputStream is = this.getClass().getResourceAsStream("LoggerTests.properties");
		Properties props = new Properties();
		props.load(is);
		is.close();
		is=null;
		
		LoggerFactory.initialize(props);
		LoggerFactoryInterface lf = LoggerFactory.getInstance();
		o = lf.getLogger(this.getClass());
		lf = null;
		
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		o = null;
	}

	
	public void testLoggerMethods()
	{
		
		o.debug("debugging");
		o.debug("debugging", new Exception("debugging"));
		
		o.warn("warning");
		o.warn("warning", new Exception("warning"));
		
		o.info("infoing");
		o.info("infoing", new Exception("infoing"));
		
		o.enter("method");
		o.enter("method", new Object[]{new Object()});
		
		o.leave("method");
		o.leave("method", new Object[]{new Object()});
		
		o.enterIn("method");
		o.enterIn("method", new Object[]{new Object()});
		
		o.leaveIn("method");
		o.leaveIn("method", new Object[]{new Object()});
		
		o.error("error");
		o.error(new Exception("error"));
		o.error("error", new Exception("error"));
		
		o.fatal("fatal");
		o.fatal(new Exception("fatal"));
		o.fatal("fatal", new Exception("fatal"));

		
		
		assertTrue(true);
		
	}

}