package org.aprestos.code.logger;

import java.util.Properties;

import org.aprestos.code.exceptions.FactoryInitializeException;
import org.aprestos.code.logger.factories.AbstractLoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerFactoryInterface;


/**
 * LoggerFactory
 * 
 * Factory class providing a log4j based logger.
 * Must be properly defined by the properties supplied 
 * with which it is initialized, this is only needed once 
 * in the VM instance lifetime.
 * The properties supplied must follow the log4j rules:
 *  #---------------------------------------------------------------------
 *  # Set root category priority to DEBUG and its only appender to A1.
 *	log4j.rootCategory=DEBUG, A1
 *	# A1 is set to be a RollingFileAppender which outputs to system.log file.
 *	log4j.appender.A1=org.apache.log4j.RollingFileAppender
 *	log4j.appender.A1.File=system.log
 *	# A1 uses PatternLayout.
 *	log4j.appender.A1.layout=org.apache.log4j.PatternLayout
 *	log4j.appender.A1.layout.ConversionPattern=%d{yyyyMMddHHmmss}[%t] %p %c\: %m%n
 *	log4j.appender.A1.MaxFileSize=100KB
 *	#----------------------------------------------------------------------
 */
public class LoggerFactory
{

	private static LoggerFactoryInterface factory;

	public static void initialize(Properties props) throws FactoryInitializeException
	{
		if (null == factory)
			factory = AbstractLoggerFactory.getFactory(props);

	}

	public static LoggerFactoryInterface getInstance()
	{
		return factory;
	}

}
