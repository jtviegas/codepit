package org.aprestos.labs.snippets.impl.jmx.simple;

public interface AMBean {

	public void doSomething();
	public String getName();
	public int getCacheSize();
	public void setCacheSize(int size);
	
}
