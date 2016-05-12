/*
 * BasicContextImpl.java
 */
/**
 * 
 */
package org.aprestos.code.bok.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.aprestos.code.bok.common.interfaces.BasicContextInterface;

/**
 * BasicContextImpl is an implementation of the BasicContextInterface.
 * 
 * @author	jtviegas
 * @version	%I%, %G%
 * 
 * @see BasicContextInterface
 */
public class BasicContextImpl implements BasicContextInterface
{
	
	private Map<Object, Object> map = new HashMap<Object, Object>();
	
	//TODO (jtv) put logger
	
	
	public void clear()
	{

		map.clear();

	}
	
        
	
	public boolean containsKey(Object key)
	{
		boolean result=false;

		result = map.containsKey(key);
		
		return result;
	}
        
        public boolean containsKey(Enum<?> key)
	{
		boolean result=false;

		result = map.containsKey(key);
		
		return result;
	}
        
        public boolean containsKey(String key)
	{
		boolean result=false;

		result = map.containsKey(key);
		
		return result;
	}

        
	
	public boolean containsValue(Object value)
	{
		boolean result = false;
		
		result = map.containsValue(value);
		
		return result;
	}
	
	
	public Set<Entry<Object, Object>> entrySet()
	{
		Set<Entry<Object, Object>> result = null;
		
		result = map.entrySet();
		
		return result;
	}

	
	public Object get(Object key)
	{
		Object result=null;

		result = map.get(key);
		
		return result;
	}

	
	public Object get(String key)
	{
		Object result=null;

		result = map.get(key);
		
		return result;
	}
        
        public Object get(Enum<?> key)
	{
		Object result=null;

		result = map.get(key);
		
		return result;
	}
	
        
	
	public Object getAndRemove(String key)
	{
		Object result=null;

		map.get(key);
		result = map.remove(key);
		
		return result;
	}
	
        public Object getAndRemove(Enum<?> key)
	{
		Object result=null;

		map.get(key);
		result = map.remove(key);
		
		return result;
	}
        public Object getAndRemove(Object key)
	{
		Object result=null;

		map.get(key);
		result = map.remove(key);
		
		return result;
	}
	
	public boolean isEmpty()
	{
		boolean result = false;
		
		result = map.isEmpty(); 
		
		return result;
	}
	
	
	public Set<Object> keySet()
	{
		
		Set<Object> result = new HashSet<Object>();
		result = map.keySet();
		
		return result;
	}
	
	
	public Object put(String key, Object value) 
	{
            Object result = null;
	
            result = map.put(key, value);
                
            return result;
	}
	
	
	public Object put(Object key, Object value)
	{
            Object result = null;
	
            result = map.put(key, value);
                
            return result;
	}
        
        public Object put(Enum<?> key, Object value)
	{
            Object result = null;
	
            result = map.put(key, value);
                
            return result;
	}
        
	@SuppressWarnings("unchecked")
	
	public void putAll(Map m)
	{

            map.putAll(m);

	}

	
	public Object remove(Object key)
	{

		Object result = null;
		
		result = map.remove(key);
		

		return result;
	}

        public Object remove(String key)
	{

		Object result = null;
		
		result = map.remove(key);
		

		return result;
	}
        
        public Object remove(Enum<?> key)
	{

		Object result = null;
		
		result = map.remove(key);
		

		return result;
	}
        
	
	public int size()
	{

		int result = 0;
		
		result = map.size();
		

		return result;
	}

	
	public Collection<Object> values()
	{

		Collection<Object> result = new ArrayList<Object>();
		
		result = map.values();
		

		return result;
	}
	

	
	public String toString()
	{
		String result = null;

		result = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("map", map).toString();

		return result;
	}
	
    
	public Object clone()
	{

    	BasicContextImpl result = null;
		
		result = new BasicContextImpl();
		
		Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry<Object, Object> entry = iterator.next();
			result.map.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

}
