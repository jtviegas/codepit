package org.aprestos.labs.snippets.impl.collections.maps;

import java.util.HashMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component("snippet")
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public SnippetImpl(){}
	
	public void go() throws Exception {
		
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("2", "B");
	    map.put("1", "A");
	    map.put("3", "C");
	    map.put("4", "D");
	    TreeMap<String, String> map2 = new TreeMap<String, String>();
	    map2.put("4", "D");
	    map2.put("2", "B");
	    map2.put("1", "A");
	    map2.put("3", "C");
	    

	    System.out.println("Are maps equal (using equals):" + map.equals(map2));

	    System.out.println("Map1:"+map.toString());
	    System.out.println("Map2:"+map2.toString());
		
	}
	
}
