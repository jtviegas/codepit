package org.aprestos.labs.snippets.impl.jmx.simple;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxRmiConnectorClient {
	
	public void go() throws Exception{
		
		System.out.println("\nCreate an RMI connector client and " +
			    "connect it to the RMI connector server");
			JMXServiceURL url = 
			    new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/AMBean");
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
			
	}

}
