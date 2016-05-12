/*
 * EnumValueDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.misc.enumsvalues;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class EnumValueDigressions extends AbstractSnippet
{

	public EnumValueDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		System.out.println(TestEnum.values()[0]);
		System.out.println(TestEnum.values()[1]);
	}

}
