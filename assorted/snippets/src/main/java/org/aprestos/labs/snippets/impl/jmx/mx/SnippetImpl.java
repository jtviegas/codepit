package org.aprestos.labs.snippets.impl.jmx.mx;

import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.springframework.stereotype.Component;


public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet {
	
	public void go() throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		ObjectName mxbeanName = new ObjectName("org.aprestos:type=B");

		Queue<String> queue = new ArrayBlockingQueue<String>(10);
		queue.add("Request-1");
		queue.add("Request-2");
		queue.add("Request-3");
		BMxBean mxbean = new B(queue);

		mbs.registerMBean(mxbean, mxbeanName);

		System.out.println("Waiting...");
		Thread.sleep(Long.MAX_VALUE);
	}

}
