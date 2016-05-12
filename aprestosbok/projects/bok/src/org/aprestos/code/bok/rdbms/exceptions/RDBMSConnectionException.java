/*
 * DbConnectionException.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.code.bok.rdbms.exceptions;

/**
 * 
 */
public class RDBMSConnectionException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public RDBMSConnectionException()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public RDBMSConnectionException(String message)
    {
	super(message);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public RDBMSConnectionException(Throwable cause)
    {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public RDBMSConnectionException(String message, Throwable cause)
    {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

}
