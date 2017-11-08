package org.aprestos.labs.snippets.impl.lang.classCloning;

//@Component("snippet")
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public SnippetImpl(){}
	
	public void go() throws Exception {
		
		Suv a = new Suv();
		Suv b = (Suv) a.cloneStructure();
		
	}
	

	
	
	
	
	
	
}
