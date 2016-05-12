package org.aprestos.labs.studies.j2ee.model;

public class TaskBaseGuiWrapper extends TaskBase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean editable;
	
	public TaskBaseGuiWrapper()
	{
		// TODO Auto-generated constructor stub
	}

	public TaskBaseGuiWrapper(String _name, int _complete, int _priority)
	{
		super(_name, _complete, _priority);
	}
	
	public TaskBaseGuiWrapper(long _id, String _name, int _complete, int _priority)
	{
		super(_id, _name, _complete, _priority);
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable()
	{
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}
	
	

}
