/*
 * RFC822Validator.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class RFC822Validator 
{
	private static final int MAX_LEN = 256;
	/**
	 * 
	 */
	public RFC822Validator()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @see ejse.validators.DocumentValidator#canInsert(java.lang.String, java.lang.String)
	 */
	public boolean canInsert(String text, String str)
	{
		boolean result=false;
		
		if ((text.length() + str.length()) <= MAX_LEN)
		{
			result = true;
		}
		
		return result;
	}


	/**
	 * 
	 * @see ejse.validators.DocumentValidator#isFormatValid(java.lang.String)
	 */

	public boolean isFormatValid(String arg0)
	{
		boolean result = false;
		
		if (arg0.equals(""))
		{
			return true;
		}
		String patternStr = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(arg0);
		boolean matchFound = matcher.matches();
		
		if (matchFound &&
				arg0.length() <= MAX_LEN
				)
		{
			result = true;
		}
		

		return result;
	}

}
