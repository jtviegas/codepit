package org.aprestos.labs.snippets.impl.dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class DatetimesTest {
    
   // @Test
    public void test() throws Exception {
	
	long expected = 1412899200000l;
	
	ISO8601CalendarDateTimeWithZoneFormatter formatter = new ISO8601CalendarDateTimeWithZoneFormatter();
   	int index = 0;
   	for(String s: inputs){
   	    	//System.out.println( "" + index++);
   		Assert.assertEquals(expected, formatter.parse(s));
  	}
   	
    }
    
    @Test
    public void test1() throws Exception {
	
	//31 Jan 2016 15:09:27 GMT
	long initial = 1454252967000l;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	Calendar startDt = new GregorianCalendar(TimeZone.getTimeZone("UTC")),
		endDt = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
	
	startDt.set(2016, 00, 30);
	endDt.set(2016, 01, 29);
	System.out.println(String.format("start: %s ... ", sdf.format(startDt.getTime())));
	startDt.add(Calendar.MONTH, 1);
	
	System.out.println(String.format("start: %s", sdf.format(startDt.getTime())));
	System.out.println(String.format("end: %s", sdf.format(endDt.getTime())));
	Assert.assertEquals(endDt, startDt);
	
	startDt.set(2016, 00, 31);
	endDt.set(2016, 01, 29);
	System.out.println(String.format("start: %s ... ", sdf.format(startDt.getTime())));
	startDt.add(Calendar.MONTH, 1);
	
	System.out.println(String.format("start: %s", sdf.format(startDt.getTime())));
	System.out.println(String.format("end: %s", sdf.format(endDt.getTime())));
	Assert.assertEquals(endDt, startDt);
	
	startDt.set(2016, 00, 29);
	endDt.set(2016, 01, 29);
	System.out.println(String.format("start: %s ... ", sdf.format(startDt.getTime())));
	startDt.add(Calendar.MONTH, 1);
	
	System.out.println(String.format("start: %s", sdf.format(startDt.getTime())));
	System.out.println(String.format("end: %s", sdf.format(endDt.getTime())));
	Assert.assertEquals(endDt, startDt);
	
	startDt.set(2016, 00, 28);
	endDt.set(2016, 01, 28);
	System.out.println(String.format("start: %s ... ", sdf.format(startDt.getTime())));
	startDt.add(Calendar.MONTH, 1);
	
	System.out.println(String.format("start: %s", sdf.format(startDt.getTime())));
	System.out.println(String.format("end: %s", sdf.format(endDt.getTime())));
	Assert.assertEquals(endDt, startDt);
	
	startDt.set(2016, 01, 28);
	endDt.set(2016, 02, 28);
	System.out.println(String.format("start: %s ... ", sdf.format(startDt.getTime())));
	startDt.add(Calendar.MONTH, 1);
	
	System.out.println(String.format("start: %s", sdf.format(startDt.getTime())));
	System.out.println(String.format("end: %s", sdf.format(endDt.getTime())));
	Assert.assertEquals(endDt, startDt);

    }
    
    @Test
    public void test2() throws Exception {
	
  
   	SimpleDateFormat sdf = new SimpleDateFormat("H");
   	Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
   	cal.setTime(sdf.parse("01:00"));
   	int hour = cal.get(Calendar.HOUR_OF_DAY);
   
   	
   	long ts1 = 1420993555000l;
   	long ts2 = 1452529556000l;
   	
   	Date d1 = new Date(ts1);
   	Date d2 = new Date(ts2);
   	
   	cal.setTime(d1);
   	int hour1 = cal.get(Calendar.HOUR_OF_DAY);
   	
   	cal.setTime(d2);
   	int hour2 = cal.get(Calendar.HOUR_OF_DAY);
   	
      	
   	
       }
    
    private String[] inputs = {
	    
	    // YYYY-MM-DD  hh:mm:ss.sss Z
	    "2014-10-10T00:00:00.000Z",
	    // YYYY-MM-DD  hh:mm:ss.sss ±hh:mm
	    "2014-10-10T00:00:00.000+00:00"  , "2014-10-10T01:00:00.000+01:00" , "2014-10-09T23:00:00.000-01:00" ,
	    // YYYY-MM-DD  hh:mm:ss.sss ±hhmm
	    "2014-10-10T00:00:00.000+0000"  , "2014-10-10T01:00:00.000+0100" , "2014-10-09T23:00:00.000-0100" ,
	    // YYYY-MM-DD  hh:mm:ss.sss ±hh
	    "2014-10-10T00:00:00.000+00"  , "2014-10-10T01:00:00.000+01" , "2014-10-09T23:00:00.000-01" ,
	    // YYYY-MM-DD  hh:mm:ss.ssssss Z
	    "2014-10-10T00:00:00.000000Z",
	    // YYYY-MM-DD  hh:mm:ss.ssssss ±hh:mm
	    "2014-10-10T00:00:00.000000+00:00"  , "2014-10-10T01:00:00.000000+01:00" , "2014-10-09T23:00:00.000000-01:00" ,
	    // YYYY-MM-DD  hh:mm:ss.ssssss ±hhmm
	    "2014-10-10T00:00:00.000000+0000"  , "2014-10-10T01:00:00.000000+0100" , "2014-10-09T23:00:00.000000-0100" ,
	    // YYYY-MM-DD  hh:mm:ss.ssssss ±hh
	    "2014-10-10T00:00:00.000000+00"  , "2014-10-10T01:00:00.000000+01" , "2014-10-09T23:00:00.000000-01" ,
	    
	    // YYYY-MM-DD  hh:mm:ss Z
	    "2014-10-10T00:00:00Z",
	    // YYYY-MM-DD  hh:mm:ss ±hh:mm
	    "2014-10-10T00:00:00+00:00"  , "2014-10-10T01:00:00+01:00" , "2014-10-09T23:00:00-01:00" ,
	    // YYYY-MM-DD  hh:mm:ss ±hhmm
	    "2014-10-10T00:00:00+0000"  , "2014-10-10T01:00:00+0100" , "2014-10-09T23:00:00-0100" ,
	    // YYYY-MM-DD  hh:mm:ss ±hh
	    "2014-10-10T00:00:00+00"  , "2014-10-10T01:00:00+01" , "2014-10-09T23:00:00-01" ,
	    
	    // YYYYMMDD  hhmmss.sss Z
	    "20141010T000000.000Z",
	    // YYYYMMDD  hhmmss.sss ±hh:mm
	    "20141010T000000.000+00:00"  , "20141010T010000.000+01:00" , "20141009T230000.000-01:00" ,
	    // YYYYMMDD  hhmmss.sss ±hhmm
	    "20141010T000000.000+0000"  , "20141010T010000.000+0100" , "20141009T230000.000-0100" ,
	    // YYYYMMDD  hhmmss.sss ±hh
	    "20141010T000000.000+00"  , "20141010T010000.000+01" , "20141009T230000.000-01" ,
	    // YYYYMMDD  hhmmss.ssssss Z
	    "20141010T000000.000000Z",
	    // YYYYMMDD  hhmmss.ssssss ±hh:mm
	    "20141010T000000.000000+00:00"  , "20141010T010000.000000+01:00" , "20141009T230000.000000-01:00" ,
	    // YYYYMMDD  hhmmss.ssssss ±hhmm
	    "20141010T000000.000000+0000"  , "20141010T010000.000000+0100" , "20141009T230000.000000-0100" ,
	    // YYYYMMDD  hhmmss.ssssss ±hh
	    "20141010T000000.000000+00"  , "20141010T010000.000000+01" , "20141009T230000.000000-01" ,
	    // YYYYMMDD  hhmmss Z
	    "20141010T000000Z",
	    // YYYYMMDD  hhmmss ±hh:mm
	    "20141010T000000+00:00"  , "20141010T010000+01:00" , "20141009T230000-01:00" ,
	    // YYYYMMDD  hhmmss ±hhmm
	    "20141010T000000+0000"  , "20141010T010000+0100" , "20141009T230000-0100" ,
	    // YYYYMMDD  hhmmss ±hh
	    "20141010T000000+00"  , "20141010T010000+01" , "20141009T230000-01" ,
    	};
    private static class ISO8601CalendarDateTimeWithZoneFormatter {
	
	private static String ZULU_TIMEZONE_PHONETIC = "Z";
	private static String ZULU_TIME_EQUIVALENT = "+0000";
	
	private static Pattern tzDesignatorPatternWithColon;
	private static Pattern tzDesignatorPatternWithOnlyHours;
	
	private SimpleDateFormat[] formatters;

	
	private SimpleDateFormat[] getFormatters(){
	    if(null == formatters){
		 String[] formats = {
				//extended format
				"yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ",
				"yyyy-MM-dd'T'HH:mm:ss.SSSZ"
				, "yyyy-MM-dd'T'HH:mm:ssZ"
				//basic format
				,"yyyyMMdd'T'HHmmss.SSSSSSZ"
				,"yyyyMMdd'T'HHmmss.SSSZ"
				,"yyyyMMdd'T'HHmmssZ"
				};
		 
		TimeZone tz = TimeZone.getTimeZone("UTC");
		formatters = new SimpleDateFormat[formats.length];
		for(int i = 0; i< formats.length; i++){
		    formatters[i] = new SimpleDateFormat(formats[i]);
		    formatters[i].setTimeZone(tz);
		    formatters[i].setLenient(false);
		}
	    }
	    return formatters;
	}
	
	private Pattern getTzDesignatorPatternWithColon() {
	    if(null == tzDesignatorPatternWithColon)
		tzDesignatorPatternWithColon = Pattern.compile("[+-][0-9]{2}:[0-9]{2}$");
	    return tzDesignatorPatternWithColon;
	}
	
	private Pattern getTzDesignatorPatternWithOnlyHours() {
	    if(null == tzDesignatorPatternWithOnlyHours)
		tzDesignatorPatternWithOnlyHours = Pattern.compile("[+-][0-9]{2}$");
	    return tzDesignatorPatternWithOnlyHours;
	}
	
	public long parse(String str) throws ParseException{
	    long result = -1;
	    String s = str;
	    if(str.endsWith(ZULU_TIMEZONE_PHONETIC)){
		s = str.substring(0, s.length()-1) + ZULU_TIME_EQUIVALENT;
	    }
	    else {
		 Matcher matcher = getTzDesignatorPatternWithColon().matcher(s);
		 if(matcher.find()){
				int offset = matcher.start();
				String timezoneDesignator = s.substring(offset).replaceAll(":", "");
				s = s.substring(0, offset) + timezoneDesignator;
		 }
		 else {
		     matcher = getTzDesignatorPatternWithOnlyHours().matcher(s);
		     if(matcher.find())
			s += "00";
		 }
	    }

	    for(SimpleDateFormat formatter:getFormatters()){
		try { 
		    result = formatter.parse(s).getTime(); 
		    break;
		} catch (ParseException e) {
		   // System.out.println(String.format("format %s failed on %s : offset location %d", format, s, e.getErrorOffset()));
		}
		catch (Exception e2) {
		    System.out.println(e2.getMessage());
		}
	    }
	    
	    if(0 > result)
		throw new ParseException("not a ISO 8601 Calendar Datetime with Zone", -1);
	    
	    return result;
	}
	
	
	
    }
    

}
