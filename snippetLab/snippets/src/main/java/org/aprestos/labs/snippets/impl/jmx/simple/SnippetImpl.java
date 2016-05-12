package org.aprestos.labs.snippets.impl.jmx.simple;

import org.springframework.stereotype.Component;


public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public SnippetImpl(){}
	
	public void go() throws Exception {
		new MBeanLoader().load();
	}
	
}
