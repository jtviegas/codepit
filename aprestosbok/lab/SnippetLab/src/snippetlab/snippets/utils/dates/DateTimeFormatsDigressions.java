/*
 * DateTimeFormatsDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.utils.dates;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	 * snippetlab.snippets.utils.dates.DateTimeFormatsDigressions
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
		this.panel.add(new JLabel(getDate() + "   " + getDateTime()), BorderLayout.CENTER);
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
		//format.applyPattern("yyyy-MM-dd");
		
        //String formattedDate = format.format(new Date(1237020343*1000L));
        //format.applyPattern("HH:mm");
        //formattedDate += " " + format.format(new Date(1237020343*1000L));
//        if(formattedDate.endsWith("+0000"))
//        {
//        	formattedDate = formattedDate.replace('+', 'Z');
//        	formattedDate = formattedDate.substring(0, formattedDate.length()-4);
//        }
        
        Calendar s = new GregorianCalendar();
        s.setTimeInMillis(1237020343*1000L);
        s.get(Calendar.DATE);
        
        return DateFormat.getDateInstance().format(new Date(1237020343*1000L)) + " - " +
        DateFormat.getTimeInstance().format(new Date(1237020343*1000L));
        
//        System.out.println("10. " + DateFormat.getDateTimeInstance(
//                DateFormat.LONG, DateFormat.LONG).format(now));
        
	}
	
}
