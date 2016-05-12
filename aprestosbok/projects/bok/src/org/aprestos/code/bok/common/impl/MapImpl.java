/*
 * MapImpl.java
 */
package org.aprestos.code.bok.common.impl;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MapImpl is a generic Map implementation class
 * 
 * @param <K>	Class type for the key object
 * @param <V>	Class type for the value object
 */
public class MapImpl<K,V> extends  AbstractMap<K,V>
{
	private List<Map.Entry<K,V>> list;

	
	  public MapImpl() 
	  {
	    list = new ArrayList<Map.Entry<K, V>>();
	  }

	  public MapImpl(List<Map.Entry<K,V>> list)
	  {
		  this.list = list;
	  }
	  
	  {
	    list = new ArrayList<Map.Entry<K, V>>();
	  }
	  
	  public MapImpl(Map<K,V> map) 
	  {
	    list = new ArrayList<Map.Entry<K, V>>();
	    putAll(map);
	  }

	  public MapImpl(int initialCapacity) 
	  {
	    list = new ArrayList<Map.Entry<K, V>>(initialCapacity);
	  }
	  

	  public Set<Map.Entry<K,V>> entrySet() 
	  {
		  Set<Map.Entry<K,V>> entries = 
			  new AbstractSet<Map.Entry<K,V>>()
			{
			  public void clear() 
			  {
				  list.clear();
			  }

			  public Iterator<Map.Entry<K,V>> iterator() 
			  {
	        	return list.iterator();
			  }

			  public int size() 
			  {
				  return list.size();
			  }
	      };

	      return entries;
	  }

	  public V put(K key, V value) 
	  {
	    int size = list.size();
	    
	    Entry entry = null;
	    
	    int i;

	      for (i = 0; i < size; i++) 
	      {
	        entry = (Entry) (list.get(i));
	      
	        if (key.equals(entry.getKey())) 
	        {
	          break;
	        }
	      }
	    
	    V oldValue = null;
	    
	    if (i < size) 
	    {
	      oldValue = entry.getValue();
	      entry.setValue(value);
	    } 
	    else 
	    {
	      list.add(new Entry(key, value));
	    }
	    
	    return oldValue;
	  }



	  private class Entry implements Map.Entry<K,V> {
		   
		  protected K key;
		  protected V value;

		    public Entry(K key, V value) 
		    {
		      this.key = key;
		      this.value = value;
		    }

		    public K getKey() {
		      return key;
		    }

		    public V getValue() {
		      return value;
		    }

		    public V setValue(V newValue) {
		    	V oldValue = value;
		      value = newValue;
		      return oldValue;
		    }

		    public boolean equals(Object o) {
		      if (!(o instanceof Map.Entry)) {
		        return false;
		      }
		      Map.Entry<?,?> e = (Map.Entry<?,?>) o;
		      return (key == null ? e.getKey() == null : key.equals(e.getKey()))
		          && (value == null ? e.getValue() == null : value.equals(e
		              .getValue()));
		    }

		    public int hashCode() {
		      int keyHash = (key == null ? 0 : key.hashCode());
		      int valueHash = (value == null ? 0 : value.hashCode());
		      return keyHash ^ valueHash;
		    }

		    public String toString() 
		    {
		      return key.toString() + "=" + value.toString();
		    }
		  }

}
