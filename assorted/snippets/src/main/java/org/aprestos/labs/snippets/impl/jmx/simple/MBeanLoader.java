package org.aprestos.labs.snippets.impl.jmx.simple;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class MBeanLoader {
	public void load() throws Exception {
		// create a MBean server
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("org.aprestos:type=AMBean");
		AMBean bean = new A();
		server.registerMBean(bean, name);
		// register the bean at the server

		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	}

}
