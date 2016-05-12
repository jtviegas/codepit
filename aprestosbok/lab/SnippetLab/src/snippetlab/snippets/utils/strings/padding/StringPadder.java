/**
 * StringPadder.java Copyright (C)Wincor-Nixdorf Portugal
 * 2009/09/02 09:39:36
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.utils.strings.padding;

import snippetlab.snippets.AbstractSnippet;

/**
 * StringPadder
 * ...description... 
 *
 * @see
 * @since
 */
public class StringPadder extends AbstractSnippet
{

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		System.out.println(padRight("Howto", 20) + "*");
		System.out.println(padLeft("Howto", 25) + "*");
		System.out.println(stringPadder(10,"Howto",true) + "*");
		System.out.println(stringPadder(10,"Howto",false) + "*");
	}
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%1$#" + n + "s", s);  
	}
	
	public static String stringPadder(int length, String string, boolean leftpadding) 
	{
        String _result = null;
        
        StringBuffer _sb = new StringBuffer();
        while (_sb.length() + string.length() < length) 
        	_sb.append(" ");
        
        if(leftpadding)
        	_result = _sb.toString() + string;
    	else
    		_result = string + _sb.toString();
        
        return _result;
   }


}
