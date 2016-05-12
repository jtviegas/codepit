package org.aprestos.labs.snippets.impl.caching.prevalence.test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendableObjectOutputStream extends ObjectOutputStream {

	public AppendableObjectOutputStream() throws IOException, SecurityException {
		super();
	}

	public AppendableObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}
	
	@Override
	  protected void writeStreamHeader() throws IOException {
	    // do not write a header, but reset:
	    // this line added after another question
	    // showed a problem with the original
	    reset();
	  }

}