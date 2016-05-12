package org.aprestos.code.bok.logger;

import java.util.Properties;

import org.aprestos.code.bok.common.exceptions.FactoryInitializeException;
import org.aprestos.code.bok.logger.factories.AbstractLoggerFactory;
import org.aprestos.code.bok.logger.interfaces.LoggerFactoryInterface;

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
