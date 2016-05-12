/*
 * SystemPropertiesDisplay.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.assorted;

import java.util.Map;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class SystemPropertiesDisplay extends AbstractSnippet
{

	/**
	 * 
	 */
	public SystemPropertiesDisplay()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{

		for(Map.Entry<Object,Object> entry:System.getProperties().entrySet())
		{
			System.out.println("------------------------------------");
			System.out.println(entry.getKey().toString());
			System.out.println(entry.getValue().toString());
		}
		
//		NTSystem system = new NTSystem();
//
//		
//		System.out.println("------------------------------------");
//		System.out.println("domain");
//		System.out.println(system.getDomain());
//		System.out.println("------------------------------------");
//		System.out.println("user");
//		System.out.println(system.getName());
		
	}
	

}
