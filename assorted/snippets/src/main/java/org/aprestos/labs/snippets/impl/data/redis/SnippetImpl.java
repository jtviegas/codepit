package org.aprestos.labs.snippets.impl.data.redis;

import java.net.URL;

import org.aprestos.labs.snippets.impl.data.redis.dao.Example;
import org.aprestos.labs.snippets.interfaces.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("snippet")
public class SnippetImpl implements Snippet {
	
	@Autowired
    private Example example;
	
	public SnippetImpl(){
		super();
	}
	
	public void go() throws Exception {
		
		System.out.println("oi");
		
		//Example o = new Example();
		example.addLink("joaovieg", new URL(
				"https://nc9098022082.tivlab.raleigh.ibm.com:16311/ibm/console/secure/securelogon.do"));

	}

}
