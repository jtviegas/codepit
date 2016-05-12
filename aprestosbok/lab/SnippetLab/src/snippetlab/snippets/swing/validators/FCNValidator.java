/*
 * FCNValidator.java Copyright (C) EID, SA.
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
public class FCNValidator
{
	static final int FCN_MAX_LENGTH = 120;
	/**
	 * 
	 */
	public FCNValidator()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @see ejse.validators.DocumentValidator#canInsert(java.lang.String, java.lang.String)
	 */

	public boolean canInsert(String text, String str)
	{
		if ((text.length() + str.length()) > FCN_MAX_LENGTH)
		{
			return false;
		}

		return str.matches("[\\w\\.-]*");
	}

	/**
	 * @see ejse.validators.DocumentValidator#canRemove(java.lang.String, int)
	 */

	public boolean canRemove(String text, int str)
	{
		return true;
	}

	/**
	 * 
	 * @see ejse.validators.DocumentValidator#isFormatValid(java.lang.String)
	 */

	public boolean isFormatValid(String text)
	{
		if (text.equals(""))
		{
			return true;
		}
		String patternStr = "^(((0|1([0-9]{1,2})?|2([0-4][0-9]?|5[0-5]?|[6-9])?)\\.){3}(0|1([0-9]{1,2})?|2([0-4][0-9]?|5[0-5]?|[6-9])?)|([a-zA-Z](([\\w]*)|([\\.][\\w])|([-][\\w]))*))$";
		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(text);
		boolean matchFound = matcher.matches();

		return matchFound;
	}
}
