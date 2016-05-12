/*
 * PropertyKey.java Copyright (C) EID, SA.
 */
package org.aprestos.code.fw;

public enum PropertyKey
{
	BUNDLES_DIR("bundles.dir"), 
	PUBLIC_INTERFACE_BUNDLE("public.interface.bundle"),
	LIBRARY_BUNDLE("library.bundle");

	
	private String key;
	
	PropertyKey(String key)
	{
		this.key = key;
	}
	
	public String getKey()
	{
		return this.key;
	}
}
