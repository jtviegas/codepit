/**
 * DirectoryListingSnippet.java Copyright (C)Wincor-Nixdorf Portugal
 * 2009/09/14 13:00:01
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.io;

import java.io.File;
import java.io.FilenameFilter;

import snippetlab.snippets.AbstractSnippet;

/**
 * DirectoryListingSnippet
 * ...description... 
 *
 * @see
 * @since
 */
public class DirectoryListingSnippet extends AbstractSnippet
{

	/**
	 * snippetlab.snippets.io.DirectoryListingSnippet
	 */
	public DirectoryListingSnippet()
	{
		
		
		
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		File f = new File("C:/Temp");
		File _f = null;
		String[] _files = f.list(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name)
			{
				if(name.substring(name.length()-4, name.length()).equalsIgnoreCase(".pdf"))
					return true;
				
				return false;
			}});
		
		for(int i=0 ; i < _files.length ; i++)
		{
			System.out.println(_files[i]);
		}

	}

}
