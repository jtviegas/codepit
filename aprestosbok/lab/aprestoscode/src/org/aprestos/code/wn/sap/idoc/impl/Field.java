/*
 * Field.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.impl;

import org.aprestos.code.wn.sap.idoc.interfaces.IField;


public class Field implements IField
{
	private String startIndex;
	private String endIndex;
	private String name;
	private String value;
	
	public Field(){}
	
	public Field(String startIndex, String endIndex, String name, String value)
	{
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.name = name;
		this.value = value;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#clone()
	 */
	public Object clone()
	{
		IField clone = new Field();
		
		if(null != this.startIndex)
			clone.setStartIndex(new String(this.startIndex));
		
		if(null != this.endIndex)
			clone.setEndIndex(new String(this.endIndex));
		
		if(null != this.name)
			clone.setName(new String(this.name));
		
		if(null != this.value)
			clone.setValue(new String(this.value));
		
		return clone;
	}
	
	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#getStartIndex()
	 */
	public String getStartIndex()
	{
		return startIndex;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#setStartIndex(java.lang.String)
	 */
	public void setStartIndex(String startIndex)
	{
		this.startIndex = startIndex;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#getEndIndex()
	 */
	public String getEndIndex()
	{
		return endIndex;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#setEndIndex(java.lang.String)
	 */
	public void setEndIndex(String endIndex)
	{
		this.endIndex = endIndex;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#getValue()
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#setValue(java.lang.String)
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#hashCode()
	 */

	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endIndex == null) ? 0 : endIndex.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((startIndex == null) ? 0 : startIndex.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.impl.IField#equals(java.lang.Object)
	 */

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (endIndex == null)
		{
			if (other.endIndex != null)
				return false;
		}
		else
			if (!endIndex.equals(other.endIndex))
				return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		if (startIndex == null)
		{
			if (other.startIndex != null)
				return false;
		}
		else
			if (!startIndex.equals(other.startIndex))
				return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else
			if (!value.equals(other.value))
				return false;
		return true;
	}


	
	
}
