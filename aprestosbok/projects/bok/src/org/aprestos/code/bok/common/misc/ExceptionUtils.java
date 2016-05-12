/*
 * ExceptionUtils.java
 */
/**
 * 
 */
package org.aprestos.code.bok.common.misc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Useful methods for exception handling.
 */
public class ExceptionUtils
{
	/**
	 * Produces a string representing the stack trace of the supplied exception.
	 * 
	 * @param e	The source exception.
	 * @return	A string representing the stack trace.
	 */
	public static String stackTrace2String(Throwable e)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(baos));
			return new String(baos.toByteArray());
		}
		catch (Exception ex)
		{
		    return "stackTrace2String failed-" + e.getMessage();
		}
	}
}
