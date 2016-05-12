package org.aprestos.labs.snippets.impl.numeric;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

//@Component("snippet")
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet {


	public void go() throws Exception {
		double d = 12345678901234567.6345;
		
		byte[] bytes = new byte[8];
	    ByteBuffer.wrap(bytes).putDouble(d);

	    double d2 = ByteBuffer.wrap(bytes).getDouble();
	    
		if(d2 == d)
			System.out.println("yes");
	}

	
}
