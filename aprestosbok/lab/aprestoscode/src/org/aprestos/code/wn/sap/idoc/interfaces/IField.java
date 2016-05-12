/*
 * IField.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.interfaces;

public interface IField
{

	public Object clone();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#getStartIndex()
	 */
	public abstract String getStartIndex();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#setStartIndex(int)
	 */
	public abstract void setStartIndex(String startIndex);

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#getEndIndex()
	 */
	public abstract String getEndIndex();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#setEndIndex(int)
	 */
	public abstract void setEndIndex(String endIndex);

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#getName()
	 */
	public abstract String getName();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#setName(java.lang.String)
	 */
	public abstract void setName(String name);

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#getValue()
	 */
	public abstract String getValue();

	/**
	 * @see org.aprestos.code.labs.idocparser.interfaces.TokenInterface#setValue(java.lang.String)
	 */
	public abstract void setValue(String value);
	
	/**
	 * @see java.lang.Object#hashCode()
	 */

	public abstract int hashCode();

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	public abstract boolean equals(Object obj);

}
