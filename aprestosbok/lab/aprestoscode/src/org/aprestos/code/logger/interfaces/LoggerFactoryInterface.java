package org.aprestos.code.logger.interfaces;



public interface LoggerFactoryInterface 
{
	public LoggerInterface getRootLogger();
	public LoggerInterface getLogger(String name);
	public LoggerInterface getLogger(Class clazz);
}
 
