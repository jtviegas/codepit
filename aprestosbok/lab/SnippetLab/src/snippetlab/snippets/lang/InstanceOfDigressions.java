/**
 * InstanceOfDigressions.java Copyright (C)Wincor-Nixdorf Portugal
 * 2009/09/02 16:32:56
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.lang;

import java.io.File;

import snippetlab.snippets.AbstractSnippet;

/**
 * InstanceOfDigressions
 * ...description... 
 *
 * @see
 * @since
 */
public class InstanceOfDigressions extends AbstractSnippet
{

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		File file = new File("C://Temp/dat.dat");
		
		if(file instanceof File)
			System.out.println("is a file!");
		
	}

}
