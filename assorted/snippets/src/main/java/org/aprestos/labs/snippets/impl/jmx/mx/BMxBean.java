package org.aprestos.labs.snippets.impl.jmx.mx;

import javax.management.MXBean;


@MXBean
public interface BMxBean {
	public QueueSample getQueueSample(); 
    public void clearQueue();
}
