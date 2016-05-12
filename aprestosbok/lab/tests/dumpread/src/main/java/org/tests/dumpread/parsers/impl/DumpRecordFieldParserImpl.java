package org.tests.dumpread.parsers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tests.dumpread.data.model.Record;
import org.tests.dumpread.exceptions.DumpReadException;
import org.tests.dumpread.parsers.interfaces.DumpRecordFieldParser;

@Component("recordfieldparser")
public class DumpRecordFieldParserImpl implements DumpRecordFieldParser 
{
	private static final Logger logger = LoggerFactory.getLogger(DumpRecordFieldParserImpl.class);
	private static final String EMPTY_DESCRIPTOR="empty";
	
	public DumpRecordFieldParserImpl() { }

	public Record parseRecord(String[] _line) throws DumpReadException
	{
		logger.debug("@parseRecord", new Object[]{_line});
		Record _o =  new Record();
		
		try {
			_o.setArticleId(Integer.parseInt(_line[0].trim()));
			
			if(null != _line[1]  && 0 < _line[1].trim().length() && (!_line[1].trim().equals(EMPTY_DESCRIPTOR)))
				_o.setArticleId(Integer.parseInt(_line[1].trim()));
			
			if(null != _line[2])
				_o.setAttribute(_line[2].trim());
			
			if(null != _line[3])
				_o.setValue(_line[3].trim());
			
			if(null != _line[4] && 0 < _line[4].trim().length() && (!_line[4].trim().equals(EMPTY_DESCRIPTOR)))
				_o.setLanguage(Short.parseShort(_line[4].trim()));
			
			if(null != _line[5] && 0 < _line[5].trim().length() && (!_line[5].trim().equals(EMPTY_DESCRIPTOR)))
				_o.setType(Short.parseShort(_line[5].trim()));
		} 
		catch (NumberFormatException e) 
		{
			throw new DumpReadException(e);
		}
		
		logger.debug("parseRecord@",_o);
		return _o;
	}

}
