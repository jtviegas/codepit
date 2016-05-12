/*
 * Driver.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.code.bok.rdbms.utils;

/**
 * 
 */
public enum Driver
{

    derby_embedded("org.apache.derby.jdbc.EmbeddedDriver"),;
        
        String name;
        
        Driver(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return this.name;
        }
    
}
