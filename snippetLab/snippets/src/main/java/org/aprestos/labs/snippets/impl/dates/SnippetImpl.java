package org.aprestos.labs.snippets.impl.dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;



//@Component("snippet")
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public static final String INPUT_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String OUTPUT_TIMESTAMP_FORMAT = "yyyyMMdd-HH-mmz";
	public static final String msg = "input:%s | time lenient.true: %s | time lenient.false: %s";
	public static final String pmsg = "[parsing exception] time lenient.true |  input:%s";
	public static final String pmsg2 = "[parsing exception] time lenient.false |  input:%s";
	public static final String msg2 = "[input] %s | [ts] %s";
	public static final String msg3 = "[intput] %s | [output] %s";
	public static final String msg4 = "[intput] %s | [output UTC] %s";
	
	private SimpleDateFormat inF, outF, inF2, outF2;
	
	private String[] inputs = {"2011-03-29T12:09:08.234UTC+00:00"
			,"2011-02-29T12:09:08.234+01:00"
				,"2011-01-01T00:01:02.000UTC+01:00"
					,"2011-01-1T00:01:02.000UTC+01:00"
					,"2011-01-111T00:01:02.000UTC+01:00"
					,"2011-01-99T00:01:02.000UTC+01:00"
					,"2011-33-99T00:01:02.000UTC+01:00"
					,"2012-02-29T00:01:02.000UTC+00:00"
					,"2012-02-29T01:01:02.000GMT+01:00"
					,"2013-02-22T00:01:02.000UTC-01:00"
					,"2002-12-29T01:01:02.000GMT-02:00"
	};
	
	public SnippetImpl(){}
	
	public void go() throws Exception {

		init();
		long timestamp = 0, timestamp2 = 0;
		String time = null, time2 = null;
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		for(String s:inputs){
			
			time = null;
			time2 = null;
			timestamp = 0;
			timestamp2 = 0;
			
			try {
				timestamp = inF.parse(s).getTime();
				System.out.println(String.format(pmsg, s));
			} catch (ParseException e) {
				System.out.println(String.format(pmsg, s));
			}
			try {
				timestamp2 = inF2.parse(s).getTime();
			} catch (ParseException e) {
				System.out.println(String.format(pmsg2, s));
			}
			time = outF.format(new Date(timestamp));
			time2 = outF.format(new Date(timestamp2));
			
			System.out.println(String.format(msg2, s, Long.toString(timestamp2)));
		}
		
		for(String s:inputs){
			
			time = null;
			time2 = null;
			timestamp = 0;
			timestamp2 = 0;

			try {
				timestamp = inF2.parse(s).getTime();
			} catch (ParseException e) {
				System.out.println(String.format(pmsg2, s));
			}
			
			time = outF.format(new Date(timestamp));
			time2 = outF2.format(new Date(timestamp));
			
			System.out.println(String.format(msg3, s, time));
			System.out.println(String.format(msg4, s, time2));
		}
		
		
		
		
		
		
	}
	
	private void init(){
		
		this.inF = new SimpleDateFormat(INPUT_TIMESTAMP_FORMAT);
		this.inF.setLenient(true);
		this.inF2 = new SimpleDateFormat(INPUT_TIMESTAMP_FORMAT);
		this.inF2.setLenient(false);
		this.outF = new SimpleDateFormat(OUTPUT_TIMESTAMP_FORMAT);
		this.outF2 = new SimpleDateFormat(OUTPUT_TIMESTAMP_FORMAT);
		this.outF2.setTimeZone(TimeZone.getTimeZone("UTC"));
		
	}
	
	

	
	
	
	
	
	
}
