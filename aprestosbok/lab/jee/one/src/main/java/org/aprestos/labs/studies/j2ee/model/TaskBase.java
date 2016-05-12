package org.aprestos.labs.studies.j2ee.model;

import java.io.Serializable;

public class TaskBase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String notes;
	private long created;
	private long started;
	private long completed;
	private int complete;
	private long predecessor=0;
	private int priority;
	
	public TaskBase()
	{
		
	}
	
	public TaskBase(String _name, int _complete, int _priority)
	{
		this.name=_name;
		this.complete = _complete;
		this.priority = _priority;
	}
	
	public TaskBase(long _id, String _name, int _complete, int _priority)
	{
		this.id = _id;
		this.name=_name;
		this.complete = _complete;
		this.priority = _priority;
	}
	
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the notes
	 */
	public String getNotes()
	{
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	/**
	 * @return the created
	 */
	public long getCreated()
	{
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(long created)
	{
		this.created = created;
	}
	/**
	 * @return the started
	 */
	public long getStarted()
	{
		return started;
	}
	/**
	 * @param started the started to set
	 */
	public void setStarted(long started)
	{
		this.started = started;
	}
	/**
	 * @return the completed
	 */
	public long getCompleted()
	{
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(long completed)
	{
		this.completed = completed;
	}
	/**
	 * @return the complete
	 */
	public int getComplete()
	{
		return complete;
	}
	/**
	 * @param complete the complete to set
	 */
	public void setComplete(int complete)
	{
		this.complete = complete;
	}
	/**
	 * @return the predecessor
	 */
	public long getPredecessor()
	{
		return predecessor;
	}
	/**
	 * @param predecessor the predecessor to set
	 */
	public void setPredecessor(long predecessor)
	{
		this.predecessor = predecessor;
	}
	/**
	 * @return the priority
	 */
	public int getPriority()
	{
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	
	
	
}
