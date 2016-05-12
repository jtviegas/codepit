/*
 * Log4JLoggerFactory.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.code.bok.logger.factories;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.aprestos.code.bok.logger.impl.Log4JLogger;
import org.aprestos.code.bok.logger.interfaces.LoggerInterface;

/**
 * 
 */
public class Log4JLoggerFactory extends  AbstractLoggerFactory
{

    /**
     * 
     */
    public Log4JLoggerFactory(Properties props)
    {
    	PropertyConfigurator.configure(props);
    }

    /**
     * @see org.aprestos.code.bok.logger.interfaces.LoggerFactoryInterface#getLogger(java.lang.String)
     */
    @Override
    public LoggerInterface getLogger(String name)
    {
	return new Log4JLogger(Logger.getLogger(name));
    }

    /**
     * @see org.aprestos.code.bok.logger.interfaces.LoggerFactoryInterface#getLogger(java.lang.Class)
     */
    @Override
    public LoggerInterface getLogger(Class<? extends Object> clazz)
    {
	return new Log4JLogger(Logger.getLogger(clazz));
    }

    /**
     * @see org.aprestos.code.bok.logger.interfaces.LoggerFactoryInterface#getRootLogger()
     */
    @Override
    public LoggerInterface getRootLogger()
    {
	return new Log4JLogger(Logger.getRootLogger());
    }


}
