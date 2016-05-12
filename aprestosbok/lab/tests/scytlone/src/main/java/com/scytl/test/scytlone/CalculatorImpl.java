package com.scytl.test.scytlone;

public class CalculatorImpl implements Calculator 
{

	private static final String DELIMITER="[\n,\\,]";
	private static final String DELIMITER_SETUP_PREFIX="//";
	//private static final String DELIMITER=",";

	public int add(String _numbers) 
	{
		int _result = 0;
		String[] _nums = null;
		String _delim = null;
		
		//handle no argument
				if( 0 == _numbers.length())
					return 0;
				
		//check forward slash
		if(_numbers.startsWith(DELIMITER_SETUP_PREFIX))
		{
			_delim = _numbers.substring(DELIMITER_SETUP_PREFIX.length(), _numbers.indexOf("\n"));
			_numbers = _numbers.substring(_numbers.indexOf("\n")+1);
		}
		else
			_delim = DELIMITER;
		
		//handle more args
		_nums = _numbers.split(_delim);
		for(String _s : _nums)
		{
			_result += Integer.parseInt(_s);
		}
	
		return _result;
	}

}
