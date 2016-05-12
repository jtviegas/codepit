/*
 * DateTimeFormatsDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.utils;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.swing.JLabel;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class DateTimeFormatsDigressions extends AbstractSnippet
{

	/**
	 * 
	 */
	public DateTimeFormatsDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		this.panel.add(new JLabel(getDate() + "\\n" + getDateTime()), BorderLayout.CENTER);
		this.panel.validate();
	}

	private String getDateTime()
	{
		SimpleDateFormat format = new SimpleDateFormat();
		TimeZone actualTimeZone = format.getTimeZone();
		TimeZone requiredTimeZone = new SimpleTimeZone(actualTimeZone.getRawOffset(),
				                                       actualTimeZone.getID());
		format.setTimeZone(requiredTimeZone);
		format.applyPattern("yyyy-MM-dd HH:mm Z");
        String formattedDate = format.format(new Date());
        if(formattedDate.endsWith("+0000"))
        {
        	formattedDate = formattedDate.replace('+', 'Z');
        	formattedDate = formattedDate.substring(0, formattedDate.length()-4);
        }
        return formattedDate;
	}
	
	private String getDate()
	{
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yyyy-MM-dd");
        String formattedDate = format.format(new Date());
//        if(formattedDate.endsWith("+0000"))
//        {
//        	formattedDate = formattedDate.replace('+', 'Z');
//        	formattedDate = formattedDate.substring(0, formattedDate.length()-4);
//        }
        return formattedDate;
	}
	
}
