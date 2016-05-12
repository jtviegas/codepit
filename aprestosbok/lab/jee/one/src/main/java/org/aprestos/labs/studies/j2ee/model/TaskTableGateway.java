package org.aprestos.labs.studies.j2ee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class TaskTableGateway implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TaskBaseGuiWrapper> tasks = new ArrayList<TaskBaseGuiWrapper>();
	
	
	public TaskTableGateway()
	{
		init();
	}

	/**
	 * @return the tasks
	 */
	public List<TaskBaseGuiWrapper> getTasks()
	{
		return this.tasks;
	}
	
	public void save()
	{
		System.out.println("saving...");
		for(TaskBaseGuiWrapper _t: this.tasks)
		{
			_t.setEditable(false);
		}
		System.out.println("...saved!");
	}
	
	private List<SelectItem> wrapArrayInSelectItems(Object[] _os)
	{
		List<SelectItem> _result = new LinkedList<SelectItem>();
		
		for(int i=0; i<_os.length; i++)
		{	
			_result.add(new SelectItem(_os[i],_os[i].toString()));
		}
		
		return _result;
	}
	
	public List<SelectItem> retrievePossiblePredecessors(long _id)
	{
		return wrapArrayInSelectItems(findPossiblePredecessors(_id));
	}
	
	private Long[] findPossiblePredecessors(long _id)
	{
		Long[] _result = null;
		
		if(0 == _id)
			 _result = new Long[this.tasks.size()];
		else
			_result = new Long[this.tasks.size() -1];
		
		int _index = 0;
	  
		for(TaskBase _t: this.tasks)
		{
			if(_id != _t.getId())
				_result[_index++]=_t.getId();
		}
		
		return _result;
	}
	
	
	private void init()
	{
		this.tasks.add(new TaskBaseGuiWrapper(2,"task 1", 20,3));
		this.tasks.add(new TaskBaseGuiWrapper(3,"task 2", 40,2));
		this.tasks.add(new TaskBaseGuiWrapper(4,"task 3", 10,1));
	}
	
}
