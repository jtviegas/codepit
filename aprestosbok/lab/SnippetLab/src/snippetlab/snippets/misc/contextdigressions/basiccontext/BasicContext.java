/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.contextdigressions.basiccontext;

import snippetlab.snippets.misc.contextdigressions.basiccontext.interfaces.BasicContextInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author jtviegas
 */
public class BasicContext implements BasicContextInterface
{
    
	
	private Map<Object, Object> map = new HashMap<Object, Object>();
	
	@Override
	public void clear()
	{

		map.clear();
	}
	
        
	@Override
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

        
	@Override
	public boolean containsValue(Object value)
	{
		boolean result = false;
		
		result = map.containsValue(value);
		
		return result;
	}
	
	@Override
	public Set<Entry<Object, Object>> entrySet()
	{
		Set<Entry<Object, Object>> result = null;
		
		result = map.entrySet();
		
		return result;
	}

	@Override
	public Object get(Object key)
	{
		Object result=null;

		result = map.get(key);
		
		return result;
	}

	@Override
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
	
        
	@Override
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
	@Override
	public boolean isEmpty()
	{
		boolean result = false;
		
		result = map.isEmpty(); 
		
		return result;
	}
	
	@Override
	public Set<Object> keySet()
	{
		
		Set<Object> result = new HashSet<Object>();
		result = map.keySet();
		
		return result;
	}
	
	@Override
	public void put(String key, Object value) 
	{

		
		map.put(key, value);
		

	}
	
	@Override
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
	@Override
	public void putAll(Map m)
	{

            map.putAll(m);

	}

	@Override
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
        
	@Override
	public int size()
	{

		int result = 0;
		
		result = map.size();
		

		return result;
	}

	@Override
	public Collection<Object> values()
	{

		Collection<Object> result = new ArrayList<Object>();
		
		result = map.values();
		

		return result;
	}
	

	@Override
	public String toString()
	{
		String result = null;

		result = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("map", map).toString();

		return result;
	}
	
    @Override
	public Object clone()
	{

		BasicContext result = null;
		
		result = new BasicContext();
		
		Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry<Object, Object> entry = iterator.next();
			result.map.put(entry.getKey(), entry.getValue());
		}

		return result;
	}


    
}
