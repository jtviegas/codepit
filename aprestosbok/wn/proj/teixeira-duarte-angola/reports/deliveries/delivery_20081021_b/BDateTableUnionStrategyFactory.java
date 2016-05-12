package com.wincor_nixdorf.tplinux.webapp.commonpt.helpers;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * BDateTableUnionStrategyFactory
 * 
 * Abstract factory class providing access to alternate sentence 
 * factories for the <i>business dated</i> tables, e.g., you'll have different
 * ways of aggregating bussiness dated tables data, whether it is an ejitem, ejheader
 * table or such as the special case presented by the invoices
 * table, which does not holds in itself a bdate column forcing the sentence
 * strategy to include such a column in the devised view/query sql sentence.
 * 
 * @author joao.viegas
 *
 */
public abstract class BDateTableUnionStrategyFactory 
{
	
	private static final Logger logger = Logger.getLogger(BDateTableUnionStrategyFactory.class);
	/**
	 * Based on the table returns the appropriate sentence for
	 * data retrieval from all the business dates tables present
	 * in the db.
	 * @param table
	 * @return
	 */
	public static BDateTableUnionStrategyFactory getStrategy(String table)
	{
		BDateTableUnionStrategyFactory result=null;
		if (table.equals("invoices") || table.equals("plumove") || table.equals("dptmove"))
		{
			result = new NoBdateColumnBDateTablesStrategy(table);
		}
		else
		{
			result = new GeneralBDateTablesStrategy(table);
		}
		
		return result;
		
	}
	
	public abstract String getSentence(List businessDates);
	
	
	protected static void do_debug(String message)
    {
    	if(logger.isDebugEnabled())
    		logger.debug(message);	
    }
    
	protected static void do_debug_enter(String message)
    {
    	do_debug("entering " + message);
    }
    
	protected static void do_debug_leave(String message, Object result)
    {
    	do_debug("leaving " + message + " with result:" + System.getProperty("line.separator")  + 
    			(null != result ? result.toString() : "null"));
    }
}
