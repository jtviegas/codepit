/*
 * AModel.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.face.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Model
{

	private int code;
	private String name;
	private boolean enabled;
	private String description;
	private List<String> listItems = new ArrayList<String>();
	private boolean on;
	private String attribute;

	public Object clone()
	{
		Model clone = new Model();
		
		clone.code = this.code;
		
		if(null != this.name)
			clone.name = new String(this.name);
		
		if(null != this.description)
			clone.description = new String(this.description);
		
		clone.enabled = this.enabled;
		
		for(String item : this.listItems)
			clone.listItems.add(new String(item));
		
		clone.on = this.on;
		
		if(null!=this.attribute)
			clone.attribute = new String(this.attribute);
		
		return clone;
	}
	/**
	 * 
	 */
	public Model(){}

	

	/**
	 * @param code
	 * @param name
	 * @param enabled
	 * @param description
	 * @param listItems
	 * @param on
	 * @param otherItems
	 */
	public Model(	int code,
					String name,
					boolean enabled,
					String description,
					List<String> listItems,
					boolean on,
					String attribute)
	{
		super();
		this.code = code;
		this.name = name;
		this.enabled = enabled;
		this.description = description;
		this.listItems = listItems;
		this.on = on;
		this.attribute = attribute;
	}



	/**
	 * @return the code
	 */
	public int getCode()
	{
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(int code)
	{
		this.code = code;
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
	 * @return the enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}


	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}


	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}


	/**
	 * @return the listItems
	 */
	public List<String> getListItems()
	{
		return listItems;
	}


	/**
	 * @param listItems the listItems to set
	 */
	public void setListItems(List<String> listItems)
	{
		this.listItems = listItems;
	}


	/**
	 * @return the on
	 */
	public boolean isOn()
	{
		return on;
	}


	/**
	 * @param on the on to set
	 */
	public void setOn(boolean on)
	{
		this.on = on;
	}



	/**
	 * @return the attribute
	 */
	public String getAttribute()
	{
		return attribute;
	}



	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}



	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + code;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((listItems == null) ? 0 : listItems.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (on ? 1231 : 1237);
		return result;
	}



	/**
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
		final Model other = (Model) obj;
		if (attribute == null)
		{
			if (other.attribute != null)
				return false;
		}
		else
			if (!attribute.equals(other.attribute))
				return false;
		if (code != other.code)
			return false;
		if (description == null)
		{
			if (other.description != null)
				return false;
		}
		else
			if (!description.equals(other.description))
				return false;
		if (enabled != other.enabled)
			return false;
		if (listItems == null)
		{
			if (other.listItems != null)
				return false;
		}
		else
			if (!listItems.equals(other.listItems))
				return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		if (on != other.on)
			return false;
		return true;
	}




	
	

}
