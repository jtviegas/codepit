package org.aprestos.labs.jaxrs.jersey;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class App extends Application {
	
	 @Override
     public Set<Class<?>> getClasses()
     {
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(MyResource.class);
        return classes;
     }
	 
	
}
