/*
 * IPAddressValidator.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.validators;

import java.util.regex.Pattern;

/**
 * 
 */
public class IPAddressValidator
{
	private final Pattern pattern =
        Pattern.compile("b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).)"
                              + "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)b");
	/**
	 * @see ejse.validators.DocumentValidator#canInsert(java.lang.String, java.lang.String)
	 */

	public boolean canInsert(String text, String str)
	{
		return true;
	}

	/**
	 * @see ejse.validators.DocumentValidator#canRemove(java.lang.String, int)
	 */

	public boolean canRemove(String text, int len)
	{
		return true;
	}

	/**
	 * @see ejse.validators.DocumentValidator#isFormatValid(java.lang.String)
	 */

	public boolean isFormatValid(String text)
	{
		return pattern.matcher(text).matches();
	}

	
}
