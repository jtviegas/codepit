package org.tests.dumpread.parsers.interfaces;

import java.io.InputStream;

import org.tests.dumpread.exceptions.DumpReadException;

/**
 * 
 * The implementing class shall read any dump file 
 * and retrieve each record iteratively
 * 
 * @author joao.viegas
 *
 */
public interface DumpFileParser 
{

	void init(InputStream _is) throws DumpReadException;
	/**
	 * reads next record in file;
	 * @return	true if successful, false if at EOF.
	 * @throws DumpReadException 
	 */
	boolean read() throws DumpReadException;
	
	/**
	 * gets the record jsut read
	 * @return
	 */
	String[] getRecord();
	
	void close() throws DumpReadException;
	
}
