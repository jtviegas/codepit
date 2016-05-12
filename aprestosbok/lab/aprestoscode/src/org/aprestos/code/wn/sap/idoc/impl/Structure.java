/*
 * IDocStructure.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.impl;

import java.util.Arrays;

import org.aprestos.code.misc.LangUtils;
import org.aprestos.code.wn.sap.idoc.interfaces.ISegment;
import org.aprestos.code.wn.sap.idoc.interfaces.IStructure;


public class Structure implements IStructure
{

	protected ISegment[] segments = null;
	
	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.IStructure#addSegment(org.aprestos.code.wn.sap.idoc.interfaces.ISegment)
	 */
	public void addSegment(ISegment segment)
	{
		int _len = 0;
		if(null != segments)
			_len = segments.length;
		else
			segments = new ISegment[]{};
		
		segments = (ISegment[])LangUtils.resizeUnidimensionalArray(segments, _len + 1);
		segments[_len] = segment;
	}
	
	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.IStructure#getSegments()
	 */
	public ISegment[] getSegments()
	{
		int _len = 0;
		if(null != segments)
			_len = segments.length;
		
		ISegment[] o = new ISegment[_len];
		for( int i = 0 ; i < _len ; i++ )
			o[i] = (ISegment)segments[i].clone();

		return o;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.IStructure#hashCode()
	 */
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Structure.arrayHashCode(segments);
		return result;
	}

	/**
	 * @see org.aprestos.code.wn.sap.idoc.interfaces.IStructure#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Structure other = (Structure) obj;
		if (!Arrays.equals(segments, other.segments))
			return false;
		return true;
	}
	
	/**
	 * Returns a hash code value for the array
	 * @param array the array to create a hash code value for
	 * @return a hash code value for the array
	 */
	private static int arrayHashCode(Object[] array)
	{
		int prime = 31;
		if (array == null)
			return 0;
		int result = 1;
		for (int index = 0; index < array.length; index++)
		{
			result = prime * result
					+ (array[index] == null ? 0 : array[index].hashCode());
		}
		return result;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException
	{
		Structure clone = new Structure();
		clone.segments = this.getSegments();
		
		return clone;
	}
	
	
	
}
