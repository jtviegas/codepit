package com.sky.dvdstore;

import com.sky.dvdstore.exceptions.DvdNotFoundException;
import com.sky.dvdstore.exceptions.InvalidTextReferenceException;

public class DvdServiceImpl implements DvdService 
{
	private static final String DVD_REFERENCE_PREFIX = "DVD-";
	private static final String INVALID_REFERENCE_EXCEPTION_TEXT = "reference must begin with'" + DVD_REFERENCE_PREFIX + "'. We got:";
	private static final String DVD_SUMMARY_FORMAT = "[%s] %s - %s...";
	private static final int NUM_OF_REVIEW_WORDS_IN_SUMMARY = 10;
	
	private DvdRepository repository;

	public DvdServiceImpl()
	{
		
	}
	
	public DvdServiceImpl(DvdRepository _repository)
	{
		this.repository = _repository;
	}
	
	/**
	 * @param repository the repository to set
	 */
	public void setRepository(DvdRepository repository) {
		this.repository = repository;
	}

	@Override
	public Dvd retrieveDvd(String _dvdReference) throws DvdNotFoundException {
		
		Dvd _result = null;
		
		if(null == _dvdReference || DVD_REFERENCE_PREFIX.length() > _dvdReference.length() || 
				(!_dvdReference.substring(0, DVD_REFERENCE_PREFIX.length()).equals(DVD_REFERENCE_PREFIX))
		)
		{
			throw new InvalidTextReferenceException(INVALID_REFERENCE_EXCEPTION_TEXT + (null==_dvdReference? "null" : _dvdReference));
		}
		
		_result = this.repository.retrieveDvd(_dvdReference);
		
		if(null == _result)
			throw new DvdNotFoundException("no dvd with reference: " + _dvdReference);
		
		return _result;
	}

	@Override
	public String getDvdSummary(String _dvdReference) throws DvdNotFoundException 
	{
		String _result = null;
		
		Dvd _dvd = this.retrieveDvd(_dvdReference);

		_result = String.format(DVD_SUMMARY_FORMAT, _dvd.getReference(),_dvd.getTitle(), 
				null == _dvd.getReview() ? "" :
				getFirstWords(_dvd.getReview(), NUM_OF_REVIEW_WORDS_IN_SUMMARY));

		return _result;
	}
	
	private String getFirstWords(String _string, int _numOfWords)
	{
		
		String[] _words = _string.split(" ");
		StringBuilder _sb = new StringBuilder();
		int _wordCount = 0;
		
		for(String _s : _words)
		{
			_sb.append(" " + _s);
			if(++_wordCount == _numOfWords)
				break;
		}

		
		return _sb.toString().trim();
	}
	
	

}

