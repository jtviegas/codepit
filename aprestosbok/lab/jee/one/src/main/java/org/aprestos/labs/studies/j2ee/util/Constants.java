package org.aprestos.labs.studies.j2ee.util;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import org.aprestos.labs.studies.j2ee.model.Priority;

@ManagedBean
@ApplicationScoped
public class Constants
{
	private List<SelectItem> completedLevels = new LinkedList<SelectItem>();
	private List<SelectItem> priorities = new LinkedList<SelectItem>();
	
	public Constants()
	{
		init();
	}
	
	public List<SelectItem> getCompletedLevels()
	{
		return completedLevels;
	}
	
	public List<SelectItem> getPriorities()
	{
		return priorities;
	}
	
	private void init()
	{
	
		for(int i=0; i<101; i+=10)
		{	
			completedLevels.add(new SelectItem(i,Integer.toString(i)));
		}
		
		for(Priority _p : Priority.values())
		{
			priorities.add(new SelectItem(_p.getLevel(),_p.getDescription()));
		}
	}
	
	public static String translate2Priority(int _p)
	{
		return Priority.values()[_p].getDescription();
	}
	
	
}
