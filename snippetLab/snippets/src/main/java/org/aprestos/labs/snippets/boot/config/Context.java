package org.aprestos.labs.snippets.boot.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Context {

	private AnnotationConfigApplicationContext appCtx;
	private static Context instance;
	
	private Context(){
		appCtx = new AnnotationConfigApplicationContext(Config.class);
		appCtx.registerShutdownHook();
	}
	
	public static synchronized Context getInstance(){
		
		if( null == instance )
			instance = new Context();
		
		return instance;
	}
	
	public ApplicationContext getApplicationContext(){
		return appCtx;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
}
