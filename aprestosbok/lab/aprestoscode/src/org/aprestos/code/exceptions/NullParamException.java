/*
 * NullParamException.java
 */
/**
 * 
 */
package org.aprestos.code.exceptions;

/**
 * 
 */
public class NullParamException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NullParamException()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NullParamException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NullParamException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NullParamException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
