/*
 * IStructure.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.interfaces;


public interface IStructure
{

	public abstract void addSegment(ISegment segment);

	public abstract ISegment[] getSegments();

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object obj);

}
