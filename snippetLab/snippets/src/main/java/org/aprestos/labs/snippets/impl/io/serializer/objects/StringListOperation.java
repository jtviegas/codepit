package org.aprestos.labs.snippets.impl.io.serializer.objects;

import java.util.List;

public abstract class StringListOperation extends Operation<List<String>, String> {

	private static final long serialVersionUID = 1L;
	protected String obj;
	
	public StringListOperation(){
		super();
	}
	
	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public abstract String execute(List<String> object) throws Exception;

}
