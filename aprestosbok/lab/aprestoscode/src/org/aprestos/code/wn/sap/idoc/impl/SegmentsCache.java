/*
 * TokenizerCache.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aprestos.code.wn.sap.idoc.interfaces.ISegment;
import org.xml.sax.SAXException;

public class SegmentsCache
{
	private static SegmentsCache cache;
	
	private static final String DESCRIPTOR_SUFFIX = ".xml";
	
	private Map map = null; // segment(UpperCase) - fileObject mappings
	private Map descriptors; // segment(UpperCase) - fileName mappings
	
	private SegmentsCache() throws IOException, SAXException 
	{
		init();
	}
	
	private boolean isSegmentLoaded(String _segment)
	{
		boolean result = false;
		
		result = map.containsKey(_segment.toUpperCase()) && null != map.get(_segment.toUpperCase());
		
		return result;
	}
	
	private void init() throws IOException, SAXException
	{
		map = new HashMap();
		loadSegmentDescriptors();
	}
	
	/**
	 * Translates file name into segment name according to 
	 * the convention <segment>.xml
	 * Note that the segment will always be handled in upper case.
	 * @param file
	 * @return
	 * @see
	 * @since
	 */
	private String getSegmentName(String file)
	{
		Pattern p = Pattern.compile( DESCRIPTOR_SUFFIX + "$");
		String[] dummy = p.split(file);
		return dummy[0].toUpperCase();
	}
	
	/**
	 * Parses the file system and finds segment descriptor files,
	 * named after the segment name and suffixed with $FILE_SUFFIX.
	 * Builds a [segment->file] name mapping.
	 * @throws IOException
	 * @see
	 * @since
	 */
	private void loadSegmentDescriptors() throws IOException
	{
		descriptors = new HashMap();
		
		Pattern p = Pattern.compile( DESCRIPTOR_SUFFIX + "$");
		
		InputStream is = SegmentsCache.class.getResourceAsStream(".");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String _file = br.readLine();
		while(null != _file)
		{
            Matcher match = p.matcher(_file);
            if(match.find())
            	descriptors.put(getSegmentName(_file), _file);
 
            _file = br.readLine();
		}

	}

	/**
	 * Loads a segment object into the cache.
	 * @param _name
	 * @throws IOException
	 * @throws SAXException
	 * @see
	 * @since
	 */
	private void loadSegment(String _name) throws IOException, SAXException
	{
		
		SegmentBuilder _builder = new SegmentBuilder();
		String _file = (String) descriptors.get(_name.toUpperCase());
		
		ISegment _segment = _builder.build(_file);
		_segment.setName(_name.toUpperCase());
		
		map.put(_name.toUpperCase(), _segment);

	}
	
	public static ISegment getSegment(String _name) throws IOException, SAXException
	{
		ISegment result = null;

		if(null == cache)
			cache = new SegmentsCache();
		
		if(cache.descriptors.containsKey(_name.toUpperCase()))
		{
			if(!cache.isSegmentLoaded(_name))
				cache.loadSegment(_name);
			
			ISegment _seg = (ISegment)cache.map.get(_name.toUpperCase());
			
			if(null != _seg)
				result = (ISegment) _seg.clone();
			
		}
		
		return result;
	}


	
	
	
	
	
}
