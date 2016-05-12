package org.tests.dumpread;

import java.io.InputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tests.dumpread.data.interfaces.RecordGW;
import org.tests.dumpread.exceptions.DumpReadException;
import org.tests.dumpread.exceptions.GWException;
import org.tests.dumpread.parsers.interfaces.DumpFileParser;
import org.tests.dumpread.parsers.interfaces.DumpRecordFieldParser;

@Component("dumpreader")
public class DumpReadImpl implements DumpReader 
{
	private static final Logger logger = LoggerFactory.getLogger(DumpReadImpl.class);
	
	@Autowired
	private DumpFileParser fileParser;
	@Autowired
	private DumpRecordFieldParser recordFieldParser;
	@Autowired
	private RecordGW recordTableGW;
	
	/* (non-Javadoc)
	 * @see org.tests.dumpread.DumpReader#readDumpIn(java.lang.String)
	 */
	public void readDumpIn(InputStream _is) throws DumpReadException
	{
		logger.debug("@readDumpIn", new Object[]{_is});
		try {
			
			//initialize the record table gateway
			recordTableGW.init();
			//initialize the reader wrapper
			fileParser.init(_is);
			
			//start transaction
			recordTableGW.startTransaction();
			
			//read file line by line with a DumpFileParser implementation
			while(fileParser.read())
			{
				//in each line, parse the record fields into a Record object using a DumpRecordFieldParser implementation
				//and persist it using the record table gateway but inside a transaction
				recordTableGW.insert(recordFieldParser.parseRecord(fileParser.getRecord()));
			}
			
			//no worries than we may commit
			recordTableGW.commitTransaction();
		} 
		catch (Exception e) 
		{
			try 
			{
				//oops something has gone wrong, let's keep the inital state
				recordTableGW.rollbackTransaction();
			} 
			catch (GWException e2) 
			{
				logger.error(ExceptionUtils.getFullStackTrace(e2));
			}
			throw new DumpReadException(e);
		}
		finally
		{
			try 
			{
				//cleansing
				recordTableGW.deinit();
				fileParser.close();
				
			} catch (Exception e3) 
			{
				logger.error(ExceptionUtils.getFullStackTrace(e3));
			}

			fileParser = null;
			recordFieldParser = null;
		}
		
		logger.debug("readDumpIn@");
	}
	

}
