/**
 * Property.java
 * copyright aprestos.org, 2010.
 */
package org.aprestos.code.labs.jasf.misc;

/**
 * 
 */
public enum Property
{
   
    ;
    
    private String key;
    
    Property(String _key)
    {
	this.key = _key;
    }
    
    public String getKey()
    {
	return this.key;
    }
}
