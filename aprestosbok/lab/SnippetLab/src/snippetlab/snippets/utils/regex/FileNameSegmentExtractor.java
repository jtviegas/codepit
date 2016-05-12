/**
 * FileNameSegmentExtractor.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/01/13 18:02:25
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import snippetlab.snippets.AbstractSnippet;

/**
 * FileNameSegmentExtractor
 * ...description... 
 *
 * @see
 * @since
 */
public class FileNameSegmentExtractor extends AbstractSnippet
{
	String files[] = new String[]{"artesana.pt","installOrganico.sh","installTu.sh","install.sh","asinstall.sh"};
	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		 for(String s : files)
	        {

	            Pattern p = Pattern.compile("^install(.*)\\.sh$");
	            Matcher match = p.matcher(s);
	            while(match.find())
	            {
	                System.out.println("found a group -> " + match.group(1));    
	            }
	            
	            //System.out.println(match.replaceAll("XXX"));
	        }
		 
		 String _s = "louisiana on my '%s'";
		 System.out.println(String.format(_s, "mind"));
		 System.out.println(String.format(_s, ""));

	}

}
