package org.tests.dumpread.parsers.impl;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tests.dumpread.exceptions.DumpReadException;
import org.tests.dumpread.parsers.interfaces.DumpFileParser;

import au.com.bytecode.opencsv.CSVReader;

@Component("fileParser")
public class DumpFileParserImpl implements DumpFileParser
{
	private static final Logger logger = LoggerFactory.getLogger(DumpFileParserImpl.class);
	
	private CSVReader reader;
	private String line[];
	
	public void init(InputStream _is) throws DumpReadException 
	{
		logger.debug("@init", new Object[]{_is});
		try 
		{
			reader = new CSVReader(new InputStreamReader(_is));
			
			//read first line and discard it for its an header
		    reader.readNext();

		} 
		catch (Exception e) 
		{
			throw new DumpReadException(e);
		}
		logger.debug("init@");
	}

	public boolean read() throws DumpReadException 
	{
		logger.debug("@read");
		boolean _b = false ;
		
		try 
		{
			_b = ((line = reader.readNext()) != null);
		} 
		catch (Exception e) 
		{
			throw new DumpReadException(e);
		}
		
		logger.debug("read@", _b);
		return _b;
	}

	public String[] getRecord() 
	{
		logger.debug("@getRecord");
		logger.debug("getRecord@", line);
		return line;
	}

	public void close() throws DumpReadException 
	{
		logger.debug("@close");
		try 
		{
			if(null != this.reader)
				this.reader.close();
			
			this.reader = null;
			
		} catch (Exception e) 
		{
			throw new DumpReadException(e);
		}
		logger.debug("close@");
	}
	
}
