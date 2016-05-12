/*
 * URLDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.assorted;

import java.io.File;
import java.net.URL;

import snippetlab.snippets.AbstractSnippet;




/**
 * 
 */
public class URLDigressions extends AbstractSnippet
{

	/**
	 * 
	 */
	public URLDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		try
		{
			File file = new File("C:\\Documents and Settings\\jtviegas\\.felix");
			URL url = file.toURI().toURL();
			
			System.out.println(url.toString());	
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
		
		

	}

}
