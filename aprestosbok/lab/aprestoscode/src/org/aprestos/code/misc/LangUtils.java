/*
 * LangUtils.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.misc;

public class LangUtils
{
	public static Object resizeUnidimensionalArray(Object oldArray, int newSize)
	{
		Object result = null;
		
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		result = java.lang.reflect.Array.newInstance(
		         elementType,newSize);
		int preserveLength = Math.min(oldSize,newSize);
		if (preserveLength > 0)
		      System.arraycopy (oldArray,0,result,0,preserveLength);

		return result;
	}
}
