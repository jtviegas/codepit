package org.aprestos.labs.snippets.impl.assorted;

import java.net.URI;

import org.springframework.stereotype.Component;


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
		URI uri = URI.create("http://www.yahoo.co.uk/a/b/c?sd=ert&ert=98");
		
		System.out.println(uri.getQuery());
		System.out.println(uri.getPath());
		
	}
	
}
