/*
 * RDBMSFactoryInterface.java
 */
/**
 * 
 */
package org.aprestos.code.bok.rdbms.interfaces;

import org.aprestos.code.bok.rdbms.exceptions.RDBMSInitializeException;

/**
 * 
 */
public interface RDBMSFactoryInterface
{
    public void initialize() throws RDBMSInitializeException;
    public RDBMSInterface getRDBMS();
    
}
