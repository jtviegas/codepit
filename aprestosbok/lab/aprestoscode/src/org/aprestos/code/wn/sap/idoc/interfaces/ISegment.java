/*
 * ISegment.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.interfaces;


public interface ISegment
{

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.IDocSegmentInterface#getName()
	 */
	public abstract String getName();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.IDocSegmentInterface#setName(java.lang.String)
	 */
	public abstract void setName(String name);

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.IDocSegmentInterface#getFields()
	 */
	public abstract IField[] getFields();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.IDocSegmentInterface#setTokens(java.util.List)
	 */
	public abstract void setFields(IField[] fields);

	public void setField(String name, String value);
	
	public String getFieldValue(String _name);
	
	public void setMandatory(boolean mandatory);
	
	public boolean isMandatory();
	
	public boolean equals(Object obj);
	
	public int hashCode();
	
	public Object clone();
	
}
