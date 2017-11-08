package org.aprestos.labs.snippets.impl.jmx.simple;

public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public SnippetImpl(){}
	
	public void go() throws Exception {
		new MBeanLoader().load();
	}
	
}
