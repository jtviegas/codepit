package org.aprestos.labs.snippets.impl.lang;

import org.springframework.stereotype.Component;

/*@Component("snippet")*/
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	
	
	public static class InnerFeature{
		
		InnerFeature(){
			super();
			System.out.println("InnerFeature creator here!");
		}
		
	}
	
	public SnippetImpl(){
		super();
	}
	
	public void go() throws Exception {
		System.out.println("SnippetImpl.go() here!");

	}
	
}
