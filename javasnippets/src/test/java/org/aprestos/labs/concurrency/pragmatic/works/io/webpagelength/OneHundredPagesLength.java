package org.aprestos.labs.concurrency.pragmatic.works.io.webpagelength;

import java.util.HashMap;
import java.util.Map;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;
import org.aprestos.labs.concurrency.pragmatic.tasks.io.webpagelength.WebPageLength;
import org.aprestos.labs.concurrency.pragmatic.works.Work;

public class OneHundredPagesLength implements Work {
	
	
	private final Map<String,Object> context;
	private final int iterations;
	private final Task task;
	
	public OneHundredPagesLength() {
		this.context = new HashMap<String,Object>();
		this.iterations = 100;
		this.task = new WebPageLength();
	}
	
	@Override
	public Map<String,Object> getContext(){
		return context;
	}
	
	@Override
	public int getIterations() {
		return iterations;
	}
	
	@Override
	public Task getTask() {
		return task;
	}
	
	
	
}
