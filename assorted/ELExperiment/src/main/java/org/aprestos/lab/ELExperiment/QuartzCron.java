package org.aprestos.lab.ELExperiment;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.quartz.CronExpression;

public class QuartzCron {

	public static enum StrictlyOnWeekdayStrategy {  GO_BACKWARD, GO_FORWARD, DO_NOTHING };
	
	
	private StrictlyOnWeekdayStrategy weekendStrategy = StrictlyOnWeekdayStrategy.DO_NOTHING;
	private CronExpression cron, cronAtMidnight;

	QuartzCron(String expression) throws ParseException{
		this.cron = new CronExpression(expression);
		this.cron.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.cronAtMidnight = new CronExpression("0 0 0 " + expression.substring(6));
		this.cronAtMidnight.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	QuartzCron(String expression, StrictlyOnWeekdayStrategy ws) throws ParseException{
		this(expression);
		this.weekendStrategy = ws;
		
	}
	
	public boolean match(long date){
		boolean result = false;
		
		MutableDateTime d = new MutableDateTime(date, DateTimeZone.UTC);
		
		
		if(weekendStrategy.equals(StrictlyOnWeekdayStrategy.DO_NOTHING))
			return cron.isSatisfiedBy(d.toDate());
		
		if(d.getDayOfWeek() == DateTimeConstants.SATURDAY || d.getDayOfWeek() == DateTimeConstants.SUNDAY) 
			return  false;
		
		if(cron.isSatisfiedBy(d.toDate()))
				return true;
			
		if(weekendStrategy.equals(StrictlyOnWeekdayStrategy.GO_BACKWARD) && d.getDayOfWeek() == DateTimeConstants.FRIDAY){
			// test the case where we have a friday that we might want to back off to
			d.addDays(1); //go to saturday
			while(d.getDayOfWeek() == DateTimeConstants.SATURDAY || d.getDayOfWeek() == DateTimeConstants.SUNDAY){
				if(cron.isSatisfiedBy(d.toDate())){
					result = true;
					break;
				}
				d.addDays(1);
			}
			
		}
		if (weekendStrategy.equals(StrictlyOnWeekdayStrategy.GO_FORWARD)  && d.getDayOfWeek() == DateTimeConstants.MONDAY ){
			// test the case where we have a monday that we might want to forward to
			d.addDays(-2);//go to saturday
			while(d.getDayOfWeek() == DateTimeConstants.SATURDAY || d.getDayOfWeek() == DateTimeConstants.SUNDAY){
				if(cron.isSatisfiedBy(d.toDate())){
					result = true;
					break;
				}
				
				d.addDays(1);
			}
				
		}
		
		return result;
	}
	
	public int getOccurrences(long start, long end){
		int result = 0;
		
		MutableDateTime ds = new MutableDateTime(start, DateTimeZone.UTC);
		ds.setTime(0, 0, 0, 0);
		MutableDateTime de = new MutableDateTime(end, DateTimeZone.UTC);
		de.setTime(0, 0, 0, 0);
		
		while(ds.isBefore(de)){
			if(match(ds.getMillis()))
				result++;

			ds.addDays(1);
		}
		
		//test the last one
		if(match(ds.getMillis()))
			result++;

		return result;
	}

}
