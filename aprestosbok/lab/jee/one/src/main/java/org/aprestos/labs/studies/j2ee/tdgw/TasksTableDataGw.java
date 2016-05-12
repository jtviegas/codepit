package org.aprestos.labs.studies.j2ee.tdgw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.aprestos.labs.studies.j2ee.model.Task;

public class TasksTableDataGw implements Serializable
{
	private long key;
	private List<Task> tasks;
	/**
	 * 
	 */
	private static final long serialVersionUID = -9087043753568263796L;

	public TasksTableDataGw()
	{
		tasks.add(new Task());
	}
	
	public TasksTableDataGw(long _key)
	{
		this.key = _key;
	}

	/**
	 * @return the tasks
	 */
	public List<Task> getTasks()
	{
		if(null == this.tasks)
			init();
		
		return this.tasks;
	}

	
	private void init()
	{
		this.tasks = new ArrayList<Task>();
	}
	
	
	
}
