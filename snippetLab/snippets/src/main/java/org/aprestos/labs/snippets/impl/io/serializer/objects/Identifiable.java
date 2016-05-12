package org.aprestos.labs.snippets.impl.io.serializer.objects;

import java.io.Serializable;

public class Identifiable implements Serializable{

	private static int count;
	private static final long serialVersionUID = 1L;
	
	private long id;

	public Identifiable() {
		synchronized (this) {
			this.id = count++;
		}
	}
	
	public long getId(){
		return this.id;
	}

}
