/*
 * IDocSegment.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aprestos.code.wn.sap.idoc.interfaces.IField;
import org.aprestos.code.wn.sap.idoc.interfaces.ISegment;


public class Segment implements ISegment
{

	protected String name = null;
	protected List fields = new ArrayList();
	protected boolean mandatory = false;
	
	
	
	/**
	 * @return the mandatory
	 */
	public boolean isMandatory()
	{
		return mandatory;
	}
	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}
	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.ISegment#getName()
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.ISegment#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setField(String _name, String _value)
	{
		IField _f = null;
		
		for(int i = 0 ; i < fields.size() ; i++ )
		{
			_f = (IField)fields.get(i);
			
			if(_f.getName().equals(_name))
			{
				_f.setValue(_value);
				break;
			}
		}
	}
	
	public String getFieldValue(String _name)
	{
		String result = null;
		IField _f = null;
		
		for(int i = 0 ; i < fields.size() ; i++ )
		{
			_f = (IField)fields.get(i);
			
			if(_f.getName().equals(_name))
			{
				result = _f.getValue();
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.ISegment#getFields()
	 */
	public IField[] getFields()
	{
		IField[] result = null;
		
		result = new IField[fields.size()];
		
		for(int i = 0 ; i < fields.size() ; i++ )
			result[i] = (IField)((IField)fields.get(i)).clone();

		return result;
	}
	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.ISegment#setFields(org.aprestos.code.wn.sap.idoc.interfaces.IField[])
	 */
	public void setFields(IField[] _fields)
	{

		if(null != _fields)
		{
			fields = new ArrayList();
			for(int i = 0 ; i < _fields.length ; i++ )
				fields.add(_fields[i]);
		}

	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Segment.fieldsHashCode(fields);
		result = prime * result + (mandatory ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		
		if (!Arrays.equals(fields.toArray(), other.fields.toArray()))
			return false;
		
		if (mandatory != other.mandatory)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		return true;
	}
	
	/**
	 * Returns a hash code value for the array
	 * @param array the array to create a hash code value for
	 * @return a hash code value for the array
	 */
	private static int fieldsHashCode(List f)
	{
		int prime = 31;

		int result = 1;
		
		for (int index = 0; index < f.size(); index++)
		{
			result = prime * result
					+ (f.get(index) == null ? 0 : f.get(index).hashCode());
		}
		
		return result;
	}
	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		ISegment clone = new Segment();
		
		clone.setMandatory(this.mandatory);
		clone.setName(new String(this.getName()));
		clone.setFields(this.getFields());
		
		return clone;
	}
	
	
	
}
