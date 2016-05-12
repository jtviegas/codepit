/*
 * Log4JLoggerFactory.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.code.logger.factories;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.aprestos.code.logger.impl.Log4JLogger;
import org.aprestos.code.logger.interfaces.LoggerInterface;


/**
 * 
 */
public class Log4JLoggerFactory extends AbstractLoggerFactory
{

	/**
     * 
     */
	public Log4JLoggerFactory(Properties props)
	{
		PropertyConfigurator.configure(props);
	}

	/**
	 * @see org.aprestos.code.logger.interfaces.LoggerFactoryInterface#getLogger(java.lang.String)
	 */

	public LoggerInterface getLogger(String name)
	{
		return new Log4JLogger(Logger.getLogger(name));
	}

	/**
	 * @see org.aprestos.code.logger.interfaces.LoggerFactoryInterface#getLogger(java.lang.Class)
	 */

	public LoggerInterface getLogger(Class clazz)
	{
		return new Log4JLogger(Logger.getLogger(clazz));
	}

	/**
	 * @see org.aprestos.code.logger.interfaces.LoggerFactoryInterface#getRootLogger()
	 */

	public LoggerInterface getRootLogger()
	{
		return new Log4JLogger(Logger.getRootLogger());
	}

}
