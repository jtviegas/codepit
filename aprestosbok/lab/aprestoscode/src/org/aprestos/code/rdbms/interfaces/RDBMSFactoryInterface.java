/*
 * RDBMSFactoryInterface.java
 */
/**
 * 
 */
package org.aprestos.code.rdbms.interfaces;

import org.aprestos.code.rdbms.exceptions.RDBMSInitializeException;

/**
 * Interface for the factory supplying RDBMS objects.
 * In order to properly use this factory you must initialize it first.
 */
public interface RDBMSFactoryInterface
{
	/**
	 * Initializes the factory, it must inject properties and
	 * find the right RDBMS object.
	 * @throws RDBMSInitializeException
	 */
    public void initialize() throws RDBMSInitializeException;
    
    /**
     * Returns the RDBMS Object according to the properties injected.
     * @return
     */
    public RDBMSInterface getRDBMS();
    
}
