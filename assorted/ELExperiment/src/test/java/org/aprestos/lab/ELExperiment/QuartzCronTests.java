package org.aprestos.lab.ELExperiment;

import java.text.ParseException;

import org.aprestos.lab.ELExperiment.QuartzCron.StrictlyOnWeekdayStrategy;
import org.junit.Before;
import org.junit.Test;

import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import junit.framework.Assert;


public class QuartzCronTests {

	private CronParser cronUtilsQuartz,cronUtilsCron4j, cronUtilsUnix;
	
	@Before
	public void setup(){
		//get a predefined instance
		CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(com.cronutils.model.CronType.QUARTZ);
		cronUtilsQuartz = new CronParser(cronDefinition);
		
		CronDefinition cronDefinition2 = CronDefinitionBuilder.instanceDefinitionFor(com.cronutils.model.CronType.CRON4J);
		cronUtilsCron4j = new CronParser(cronDefinition2);
		
		CronDefinition cronDefinition3 = CronDefinitionBuilder.instanceDefinitionFor(com.cronutils.model.CronType.UNIX);
		cronUtilsUnix = new CronParser(cronDefinition3);
				
	}
	
	
	
/*	patterns 
	1. payday 23rd, back off to previous working day 
	2. last Friday of a month 
	3. first/second/third Friday of a month 
	4. holidays  
		4.1 fixed holidays 25th Dec 
		4.2 dynamic holidays (Thanksgiving: fourth Thursday of November)  */
	
	
	/**
	 * 6 major US holidays matching with unix cron parser
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test_pattern_6() throws ParseException {
		
		// January 1 - New Year's Day | February 2 - Groundhog Day | First Sunday of Feb - Super Bowl Sunday | February 14 - Valentine's Day | March 17 - St. Patrick's Day
		// April 1 - April Fool's Day |
		
		String[] expression = {
				"* * 1 1 *",  "* * 2 2 *", "* * 1,2,3,4,5,6,7 2 0", "* * 14 2 *", "* * 17 3 *"
				, "* * 1 4 *"
		};
		long[] tss = {
				1451613722000l, 1454378522000l, 1454810522000l, 1455415322000l,1458180122000l
				, 1459476122000l
		};
		
		QuartzCron cron = new QuartzCron(expression);

		// Fri, 25 Mar 2016 08:23:45 GMT
		long t1 = 1458894225000l
		// Thu, 31 Mar 2016 08:23:45 GMT
		, t2 =  1459412625000l
		// Thu, 19 Nov 2015 08:23:45 GMT
		, t3 = 1447921425000l
		// Thu, 03 Mar 2016 08:23:45 GMT
		, t4 = 1456993425000l 
		;
		
		Assert.assertTrue(cron.match(t1));
		Assert.assertTrue(cron.match(t2));
		Assert.assertFalse(cron.match(t3));
			//Assert.assertFalse(cron.match(new Date(t4)));

		
		//cronUtilsQuartz.parse(expression); // -> java.lang.IllegalArgumentException: Invalid chars in expression! Expression: 7L Invalid chars
		//cronUtilsCron4j.parse(expression_withNoSecondsField); // -> java.lang.IllegalArgumentException: Invalid chars in expression! Expression: 7L Invalid chars: L
		//cronUtilsUnix.parse(expression_withNoSecondsField); // -> java.lang.IllegalArgumentException: Invalid chars in expression! Expression: 7L Invalid chars: L
	}
	
	/**
	 * 5 Quarterly activities.

  			Such as end of quarter like they have in finance.  This means increased activity 
  			certainly in the last week of the quarter (including saturdays and sundays) 
  			with probably mental things happening on the very last day.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test_pattern_5() throws ParseException {
		
		String expression = "* * * ? 3 1-7L";
		String expression_withNoSecondsField = "* * ? 3 1-7L";
		
		QuartzCron cron = new QuartzCron(expression);

		// Fri, 25 Mar 2016 08:23:45 GMT
		long t1 = 1458894225000l
		// Thu, 31 Mar 2016 08:23:45 GMT
		, t2 =  1459412625000l
		// Thu, 19 Nov 2015 08:23:45 GMT
		, t3 = 1447921425000l
		// Thu, 03 Mar 2016 08:23:45 GMT
		, t4 = 1456993425000l 
		;
		
		Assert.assertTrue(cron.match(t1));
		Assert.assertTrue(cron.match(t2));
		Assert.assertFalse(cron.match(t3));
			//Assert.assertFalse(cron.match(new Date(t4)));

		
		//cronUtilsQuartz.parse(expression); // -> java.lang.IllegalArgumentException: Invalid chars in expression! Expression: 7L Invalid chars
		//cronUtilsCron4j.parse(expression_withNoSecondsField); // -> java.lang.IllegalArgumentException: Invalid chars in expression! Expression: 7L Invalid chars: L
		//cronUtilsUnix.parse(expression_withNoSecondsField); // -> java.lang.IllegalArgumentException: Invalid chars in expression! Expression: 7L Invalid chars: L
	}
	
	/**
	 * 4.2 dynamic holidays (Thanksgiving: fourth Thursday of November)
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test_pattern_42() throws ParseException {

		String expression = "* * * ? 11 5#4";
		QuartzCron cron = new QuartzCron(expression);

		// Thu, 22 Nov 2012 08:23:45 GMT
		long t1 = 1353572625000l
		// Thu, 19 Nov 2015 08:23:45 GMT
		, t2 = 1447921425000l
		// Thu, 24 Nov 2016 08:23:45 GMT
		, t3 = 1479975825000l
		;
		
		Assert.assertTrue(cron.match(t1));
		Assert.assertFalse(cron.match(t2));
		Assert.assertTrue(cron.match(t3));
		
		Assert.assertEquals(3, cron.getOccurrences(t1,t2));
		Assert.assertEquals(5, cron.getOccurrences(t1,t3));
		
	}
	
	/**
	 * 4.1 fixed holidays - new year's day
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test_pattern_41() throws ParseException {

		String expression = "* * * 1 1 ?";
		QuartzCron cron = new QuartzCron(expression);

		// Sun, 01 Jan 2012 08:23:45 GMT
		long t1 = 1325406225000l
		// Fri, 13 Nov 2015 08:23:45 GMT
		, t2 = 1447403025000l
		// Fri, 01 Jan 2016 08:23:45 GMT
		, t3 = 1451636625000l
		;
		
		Assert.assertTrue(cron.match(t1));
		Assert.assertFalse(cron.match(t2));
		Assert.assertTrue(cron.match(t3));
		
		Assert.assertEquals(4, cron.getOccurrences(t1, t2));
		Assert.assertEquals(5, cron.getOccurrences(t1, t3));
		
	}
	
	/**
	 * 3. second Friday of a month 
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test_pattern_3() throws ParseException {
		
		String expression = "* * * ? * 6#2";
		QuartzCron cron = new QuartzCron(expression);
		
		// Fri, 13 Nov 2015 08:23:45 GMT
			long t1 = 1447403025000l
			// Fri, 15 Jan 2016 08:23:45 GMT
			, t2 = 1452846225000l
			// Fri, 11 Mar 2016 08:23:45 GMT
			, t3 = 1457684625000l
			;
				
		
		Assert.assertTrue(cron.match(t1));
		Assert.assertFalse(cron.match(t2));
		Assert.assertTrue(cron.match(t3));
		
		Assert.assertEquals(3, cron.getOccurrences(t1, t2));
		Assert.assertEquals(5, cron.getOccurrences(t1, t3));
		
	}
	
	/**
	 * 2. last Friday of a month
	 * 
	 * @throws ParseException
	 */
	@Test
	public void test_pattern_2() throws ParseException {
		
		String expression = "* * * ? * 6L";
		QuartzCron cron = new QuartzCron(expression);
		
		//Fri, 27 Nov 2015 08:23:45 GMT
		long t1 = 1448612625000l
		// Fri, 22 Jan 2016 08:23:45 GMT
		, t2 = 1453451025000l
		// Fri, 25 Mar 2016 08:23:45 GMT
		, t3 = 1458894225000l
		;
		
		Assert.assertTrue(cron.match(t1));
		Assert.assertFalse(cron.match(t2));
		Assert.assertTrue(cron.match(t3));
		
		Assert.assertEquals(2, cron.getOccurrences(t1, t2));
		Assert.assertEquals(5, cron.getOccurrences(t1,t3));
		
	}
	/**
	 * 1. payday 23rd, back off/forward to previous/next working day
	 * @throws ParseException 
	 */
	@Test
	public void test_pattern_1() throws ParseException {
		
		String expression = "* * * 23 * ?";

		// Sun, 23 Aug 2015 08:23:45 GMT
		long t1 = 1440318225000l
		// Mon, 24 Aug 2015 08:23:45 GMT
		, t2 = 1440404625000l
		// Wed, 23 Dec 2015 08:23:45 GMT
		, t3 = 1450859025000l
		// Fri, 22 Jan 2016 08:23:45 GMT
		, t4 = 1453451025000l
		//Sat, 23 Jan 2016 08:23:45 GMT
		, t5 = 1453537425000l 
		// Tue, 23 Aug 2016 08:23:45 GMT
		, t6 = 1471940625000l ;
		
		QuartzCron cron = new QuartzCron(expression,StrictlyOnWeekdayStrategy.GO_BACKWARD);
		
		Assert.assertFalse(cron.match(t1));
		Assert.assertFalse(cron.match(t2));
		Assert.assertTrue(cron.match(t3));
		Assert.assertTrue(cron.match(t4));
		Assert.assertFalse(cron.match(t5));
		Assert.assertTrue(cron.match(t6));
		
		Assert.assertEquals(0, cron.getOccurrences(t1, t2));
		Assert.assertEquals(4, cron.getOccurrences(t1, t3));
		Assert.assertEquals(5, cron.getOccurrences(t1, t4));
		Assert.assertEquals(5, cron.getOccurrences(t1, t5));
		Assert.assertEquals(12, cron.getOccurrences(t1, t6));
		
		cron = new QuartzCron(expression,StrictlyOnWeekdayStrategy.GO_FORWARD);
		
		Assert.assertFalse(cron.match(t1));
		Assert.assertTrue(cron.match(t2));
		Assert.assertTrue(cron.match(t3));
		Assert.assertFalse(cron.match(t4));
		Assert.assertFalse(cron.match(t5));
		Assert.assertTrue(cron.match(t6));
		
		Assert.assertEquals(1, cron.getOccurrences(t1, t2));
		Assert.assertEquals(5, cron.getOccurrences(t1, t3));
		Assert.assertEquals(5, cron.getOccurrences(t1, t4));
		Assert.assertEquals(5, cron.getOccurrences(t1, t5));
		Assert.assertEquals(13, cron.getOccurrences(t1, t6));
			
	}
	

}
