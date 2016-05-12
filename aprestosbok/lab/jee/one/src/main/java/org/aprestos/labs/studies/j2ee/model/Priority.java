package org.aprestos.labs.studies.j2ee.model;

import java.io.Serializable;

public enum Priority implements Serializable
{
	TODAY(0,"now"), ASAP(1, "asap"), MONTH(2,"in a month"), HALF_A_YEAR(3,"in half a year"), ONE_DAY(4,"one day");
	
	
	private int level;
	private String description;
	
	private Priority(int _level, String _desc)
	{
		this.level = _level;
		this.description = _desc;
	}

	/**
	 * @return the index
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	
}

