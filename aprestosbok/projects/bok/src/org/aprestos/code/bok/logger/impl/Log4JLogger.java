package org.aprestos.code.bok.logger.impl;

import org.aprestos.code.bok.logger.interfaces.LoggerInterface;

public class Log4JLogger implements LoggerInterface 
{
 
    	private static final String X ="X";
    	private static final String ENTER_INT = "enter private[" + X + "]";
    	private static final String LEAVE_INT = "leave private[" + X + "]";
    	private static final String ENTER = "enter[" + X + "]";
    	private static final String LEAVE = "leave[" + X + "]";
    	private static final String PARAMS_INTRO = "with parameters:";
    	private static final String RETVAL_INTRO = "with return value:";
    	private static final String PARAMS_PREFIX = "-> ";
    	private static final String DEFAULT_ERROR_MESSAGE = "OOPPSS";
    	
    	private org.apache.log4j.Logger logger;
    
    	public Log4JLogger(org.apache.log4j.Logger logger)
    	{
    	    this.logger = logger;
    	}
    	
    	
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#enter(java.lang.String, java.lang.Object[])
	 */
	public void enter(String method, Object[] p) 
	{
	    String message = ENTER.replaceAll(X, method) + " " + PARAMS_INTRO + 
	    	System.getProperty("line.separator") + objArray2String(p);
	    
	    this.logger.debug(message);   
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#leave(java.lang.String, java.lang.Object)
	 */
	public void leave(String method, Object result) 
	{
	    String message = LEAVE.replaceAll(X, method) + " " + RETVAL_INTRO + 
	    	System.getProperty("line.separator") + (null == result ? "null" : result.toString());
	    this.logger.debug(message); 
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#enter(java.lang.String)
	 */
	public void enter(String method) 
	{
	    this.logger.debug(ENTER.replaceAll(X, method));
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#leave(java.lang.String)
	 */
	public void leave(String method) 
	{
	    this.logger.debug(LEAVE.replaceAll(X, method));
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#enterIn(java.lang.String, java.lang.Object[])
	 */
	public void enterIn(String method, Object[] p) 
	{
	    String message = ENTER_INT.replaceAll(X, method) + " " + PARAMS_INTRO + 
	    	System.getProperty("line.separator") + objArray2String(p);
	    
	    this.logger.debug(message); 
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#leaveIn(java.lang.String, java.lang.Object)
	 */
	public void leaveIn(String method, Object result) 
	{
	    String message = LEAVE_INT.replaceAll(X, method) + " " + RETVAL_INTRO + 
	    	System.getProperty("line.separator") + (null == result ? "null" : result.toString());
	    this.logger.debug(message);   
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#enterIn(java.lang.String)
	 */
	public void enterIn(String method) 
	{
	    this.logger.debug(ENTER_INT.replaceAll(X, method));
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#leaveIn(java.lang.String)
	 */
	public void leaveIn(String method) 
	{
	    this.logger.debug(LEAVE_INT.replaceAll(X, method));
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#debug(java.lang.String)
	 */
	public void debug(String message) 
	{
	    this.logger.debug(message);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#debug(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String message, Throwable t) 
	{
	    this.logger.debug(message, t);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable t) 
	{
	    this.logger.info(message, t);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#info(java.lang.String)
	 */
	public void info(String message) 
	{
	    this.logger.info(message);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String message, Throwable t) 
	{
	    this.logger.warn(message, t);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#warn(java.lang.String)
	 */
	public void warn(String message) 
	{
	    this.logger.warn(message);
	}

	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String message, Throwable t) 
	{
	    this.logger.error(message, t);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#error(java.lang.String)
	 */
	public void error(String message) 
	{
	    this.logger.error(message);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#error(java.lang.Throwable)
	 */
	public void error(Throwable t) 
	{
	    this.logger.error(DEFAULT_ERROR_MESSAGE, t);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#fatal(java.lang.String, java.lang.Throwable)
	 */
	public void fatal(String message, Throwable t) 
	{
	    this.logger.fatal(message, t);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#fatal(java.lang.String)
	 */
	public void fatal(String message) 
	{
	    this.logger.fatal(message);
	}
	 
	/**
	 *@see org.aprestos.code.bok.logger.interfaces.LoggerInterface#fatal(java.lang.Throwable)
	 */
	public void fatal(Throwable t) 
	{
	    this.logger.fatal(DEFAULT_ERROR_MESSAGE, t);
	}
	
	
	private String objArray2String(Object[] os)
    	{
    	    StringBuffer result=new StringBuffer();
    	    
    	    if(null != os)
    	    {
    		for(Object o:os)
        	    {
        		if(null != o)
        		{
        		    result.append(PARAMS_PREFIX);
        		    result.append(o.toString());
        		    result.append(System.getProperty("line.separator")); 
        		}
        	    }
    	    }
    	    
    	    return result.toString();
    	}
	 
}
 
