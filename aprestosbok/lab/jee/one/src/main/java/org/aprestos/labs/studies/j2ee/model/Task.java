/**
 * 
 */
package org.aprestos.labs.studies.j2ee.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

/**
 * @author joao.viegas
 *
 */
@ManagedBean
public class Task implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2275065524676076446L;
	private long id;
	private String name;
	private String notes;
	private long created;
	private long started;
	private long completed;
	private int complete;
	private long predecessor=0;
	private int priority;
	
	private List<SelectItem> taskItems = new LinkedList<SelectItem>();
	private static final int TASK_LIST_LENGTH=7;
	private static final String TASK_DESC="Task %d";
	
	private List<SelectItem> priorities = new LinkedList<SelectItem>();
	
	private  String idLabel = "ID";
	private  String nameLabel = "name";
	private  String notesLabel = "notes";
	private  String completeLabel = "complete";
	private  String predecessorLabel = "predecessor";
	private  String priorityLabel = "priority";
	private  String submitLabel = "submit";
	private  String requireCompleteMsg="ooops must set complete";
	private  String requireNameMsg="ooops must set name";
	private  String formLabel = "Task";
	
	
	
	public Task()
	{
		
		
		taskItems.add(new SelectItem((new Long(0)).longValue(),"no predecessor"));
		taskItems.add(new SelectItem((new Long(1)).longValue(),"task 1"));
		taskItems.add(new SelectItem((new Long(2)).longValue(),"task 2"));
		taskItems.add(new SelectItem((new Long(3)).longValue(),"task 3"));
		taskItems.add(new SelectItem((new Long(4)).longValue(),"task 4"));
		taskItems.add(new SelectItem((new Long(5)).longValue(),"task 5"));
		
		
		priorities.add(new SelectItem(Priority.ONE_DAY.getLevel(),Priority.ONE_DAY.getDescription()));
		priorities.add(new SelectItem(Priority.HALF_A_YEAR.getLevel(),Priority.HALF_A_YEAR.getDescription()));
		priorities.add(new SelectItem(Priority.MONTH.getLevel(),Priority.MONTH.getDescription()));
		priorities.add(new SelectItem(Priority.ASAP.getLevel(),Priority.ASAP.getDescription()));
		priorities.add(new SelectItem(Priority.TODAY.getLevel(),Priority.TODAY.getDescription()));
		
		
	}
	
	
	
	/**
	 * @return the priorities
	 */
	public List<SelectItem> getPriorities()
	{
		return priorities;
	}



	/**
	 * @return the taskItems
	 */
	public List<SelectItem> getTaskItems()
	{
		return taskItems;
	}



	/**
	 * @return the formLabel
	 */
	public  String getFormLabel()
	{
		return formLabel;
	}
	/**
	 * @return the idLabel
	 */
	public  String getIdLabel()
	{
		return idLabel;
	}
	/**
	 * @return the requireNameMsg
	 */
	public  String getRequireNameMsg()
	{
		return requireNameMsg;
	}
	/**
	 * @return the requireCompleteMsg
	 */
	public  String getRequireCompleteMsg()
	{
		return requireCompleteMsg;
	}
	/**
	 * @return the submitLabel
	 */
	public  String getSubmitLabel()
	{
		return submitLabel;
	}
	/**
	 * @return the nameLabel
	 */
	public  String getNameLabel()
	{
		return nameLabel;
	}
	/**
	 * @return the notesLabel
	 */
	public  String getNotesLabel()
	{
		return notesLabel;
	}
	/**
	 * @return the completeLabel
	 */
	public  String getCompleteLabel()
	{
		return completeLabel;
	}
	/**
	 * @return the predecessorLabel
	 */
	public  String getPredecessorLabel()
	{
		return predecessorLabel;
	}
	/**
	 * @return the priorityLabel
	 */
	public  String getPriorityLabel()
	{
		return priorityLabel;
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
	

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + complete;
		result = prime * result + (int) (completed ^ (completed >>> 32));
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + (int) (predecessor ^ (predecessor >>> 32));
		result = prime * result + priority;
		result = prime * result + (int) (started ^ (started >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (complete != other.complete)
			return false;
		if (completed != other.completed)
			return false;
		if (created != other.created)
			return false;
		if (id != other.id)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null)
		{
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (predecessor != other.predecessor)
			return false;
		if (priority != other.priority)
			return false;
		if (started != other.started)
			return false;
		return true;
	}
	public Object clone()
	{
		Task _o = new Task();
		
		_o.name=new String(this.name);
		_o.notes=new String(this.notes);
		_o.created = this.created;
		_o.started = this.started;
		_o.completed = this.completed;
		_o.complete = this.complete;
		_o.predecessor = this.predecessor;
		_o.priority = this.priority;
		
		return _o;
	}
	
	
}
