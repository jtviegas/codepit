package org.aprestos.labs.snippets.impl.serialization.proxies;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

public class ToBePersisted implements Obj {

    private final String name;
    private final int number;
    private final boolean status;
    private final Map<String, String> config;

    public ToBePersisted(String name, int number, boolean status,
	    Map<String, String> config) {
	this.name = name;
	this.number = number;
	this.status = status;
	this.config = config;
    }

    /* (non-Javadoc)
     * @see org.aprestos.labs.snippets.impl.serialization.proxies.Obj#getName()
     */
    public String getName() {
	return name;
    }

    /* (non-Javadoc)
     * @see org.aprestos.labs.snippets.impl.serialization.proxies.Obj#getNumber()
     */
    public int getNumber() {
	return number;
    }

    /* (non-Javadoc)
     * @see org.aprestos.labs.snippets.impl.serialization.proxies.Obj#isStatus()
     */
    public boolean isStatus() {
	return status;
    }

    /* (non-Javadoc)
     * @see org.aprestos.labs.snippets.impl.serialization.proxies.Obj#getConfig()
     */
    public Map<String, String> getConfig() {
	return config;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((config == null) ? 0 : config.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + number;
	result = prime * result + (status ? 1231 : 1237);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ToBePersisted other = (ToBePersisted) obj;
	if (config == null) {
	    if (other.config != null)
		return false;
	} else if (!config.equals(other.config))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (number != other.number)
	    return false;
	if (status != other.status)
	    return false;
	return true;
    }
    
    private void readObject(ObjectInputStream stream) throws InvalidObjectException{
	throw new InvalidObjectException("serialization proxy required");
    }
    
    private Object writeReplace(){
	return new SerializationProxy(this);
    }
    
    private static class SerializationProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 59216565393219115L;
	private final String name;
	private final int number;
	private final boolean status;
	private final Map<String, String> config;

	SerializationProxy(ToBePersisted o) {
	    this.name = o.name;
	    this.number = o.number;
	    this.status = o.status;
	    this.config = o.config;
	}
	
	private Object readResolve(){
	    return new ToBePersisted(this.name, this.number, this.status, this.config);
	}

    }

}
