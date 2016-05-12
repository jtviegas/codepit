package org.aprestos.labs.snippets.impl.jmx.mx;

import java.util.Date;
import java.util.Queue;

public class B implements BMxBean {

	 private Queue<String> queue; 
     
	    public B (Queue<String> queue) { 
	        this.queue = queue; 
	    } 
	         
	    public QueueSample getQueueSample() { 
	        synchronized (queue) { 
	            return new QueueSample(new Date(), 
	                           queue.size(), queue.peek()); 
	        } 
	    } 
	         
	    public void clearQueue() { 
	        synchronized (queue) { 
	            queue.clear(); 
	        } 
	    } 

}
