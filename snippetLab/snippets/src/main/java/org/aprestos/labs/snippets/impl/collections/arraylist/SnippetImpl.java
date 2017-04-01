package org.aprestos.labs.snippets.impl.collections.arraylist;

import java.util.ArrayList;

import org.springframework.stereotype.Component;


public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public SnippetImpl(){}
	
	public void go() throws Exception {
		ArrayList<String> o = new ArrayList<String>();
		o.add("w");
		o.add(2, "v");
		o.add("x");
		
	}
	
}
