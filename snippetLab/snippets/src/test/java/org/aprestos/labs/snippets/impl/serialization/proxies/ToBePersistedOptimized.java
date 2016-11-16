package org.aprestos.labs.snippets.impl.serialization.proxies;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ToBePersistedOptimized implements Obj {

    private static Set<Map<String,String>> configCache;
    
    private final String name;
    private final int number;
    private final boolean status;
    private final Map<String, String> config;

    public ToBePersistedOptimized(String name, int number, boolean status,
	    Map<String, String> config) {
	this.name = name;
	this.number = number;
	this.status = status;
	this.config = config;
    }

    public String getName() {
	return name;
    }

    public int getNumber() {
	return number;
    }

    public boolean isStatus() {
	return status;
    }

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
	ToBePersistedOptimized other = (ToBePersistedOptimized) obj;
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
    
    private static Map<String, String> findPrototype(Map<String, String> config){
	    Map<String, String> result = null;
	    
	    for(Map<String, String> map: configCache){
		if(Arrays.equals(map.entrySet().toArray(), config.entrySet().toArray())){
		    result = map;
		    break;
		}
	    }
	    
	    if(null == result){
		configCache.add(config);
		result = config;
	    }
	    
	    return result;
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

	SerializationProxy(ToBePersistedOptimized o) {
	    this.name = o.name;
	    this.number = o.number;
	    this.status = o.status;
	    this.config = o.config;
	}
	
	private Object readResolve(){
	    Map<String, String> conf = findPrototype(this.config);
	    return new ToBePersistedOptimized(this.name, this.number, this.status, conf);
	}
	
	

    }

}
