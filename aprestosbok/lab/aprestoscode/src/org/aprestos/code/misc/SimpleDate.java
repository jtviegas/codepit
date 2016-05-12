/**
 * SimpleDate.java copyright aprestos.org, 2008.
 */
package org.aprestos.code.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Simple definition for a date, assuming always the time as 00:00:00:000 .
 */
public class SimpleDate
{
	private GregorianCalendar _cal;

	public SimpleDate(String date, String pattern) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat
	     (pattern);
	   Date d = format.parse(date);
	   GregorianCalendar _c = new GregorianCalendar();
	   _c.setTime(d);
	   this._cal = _c;
	}
	
	public SimpleDate(String date) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat
	     ("dd-MMM-yyyy HH:mm:ss");
	   Date d = format.parse(date);
	   GregorianCalendar _c = new GregorianCalendar();
	   _c.setTime(d);
	   this._cal = _c;
	}
	
	public SimpleDate(long timeInMillis)
	{
		GregorianCalendar _c = new GregorianCalendar();
		_c.setTimeInMillis(timeInMillis);
		this._cal = _c;
	}
	
	
	public SimpleDate(GregorianCalendar gcal)
	{
		this._cal = gcal;
	}

	public SimpleDate(Date date)
	{
		GregorianCalendar _c = new GregorianCalendar();
		_c.setTimeInMillis(date.getTime());
		this._cal = _c;
	}
	
	public SimpleDate(java.sql.Date date)
	{
		GregorianCalendar _c = new GregorianCalendar();
		_c.setTimeInMillis(date.getTime());
		this._cal = _c;
	}
	
	public SimpleDate(int year, int month, int day)
	{
		this._cal = initialize(new GregorianCalendar(year, month - 1, day));
	}

	public java.sql.Date toSQLDate()
	{
		java.sql.Date result = new java.sql.Date(_cal.getTimeInMillis());
		return result;
	}

	public Date toDate()
	{
		Date result = new Date(_cal.getTimeInMillis());
		return result;
	}
	
	public SimpleDate addDays(int days)
	{
		GregorianCalendar _c = new GregorianCalendar();
		_c.setTimeInMillis(_cal.getTimeInMillis());
		_c.add(GregorianCalendar.DAY_OF_YEAR, days);
		
		return new SimpleDate(_c);
	}
	
	
	
	private GregorianCalendar initialize(GregorianCalendar gcal)
	{
		GregorianCalendar result = gcal;
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}

}
