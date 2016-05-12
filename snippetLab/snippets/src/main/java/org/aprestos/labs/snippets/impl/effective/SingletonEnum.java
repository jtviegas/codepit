package org.aprestos.labs.snippets.impl.effective;

public enum SingletonEnum {
    INSTANCE;
    
    private static int index = 0;
    
    static {
	index++;
    }
    
    public String get(){
	return "SingletonEnum " + index;
    }
}
