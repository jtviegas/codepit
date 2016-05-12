/**
 * SimpleDate.java
 * copyright aprestos.org, 2008.
 */
package org.aprestos.code.bok.common.misc;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Simple definition for a date, assuming always the time
 * as 00:00:00:000 .
 */
public class SimpleDate
{
    private GregorianCalendar _cal;
    
    public SimpleDate(GregorianCalendar gcal)
    {
	this._cal = initialize(gcal);
    }
    
    public SimpleDate(int year, int month, int day)
    {
	this._cal = initialize(new GregorianCalendar(year, month - 1, day));
    }
    
    public Date toSQLDate()
    {
	Date result = new Date(_cal.getTimeInMillis());
	
	return result;
    }
    
    private GregorianCalendar initialize(GregorianCalendar gcal)
    {
	GregorianCalendar result = gcal;
        result.set(Calendar.HOUR_OF_DAY,0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result;
    }
    
}
