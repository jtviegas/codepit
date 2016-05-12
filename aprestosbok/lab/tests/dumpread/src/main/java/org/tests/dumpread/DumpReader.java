package org.tests.dumpread;

import java.io.InputStream;

import org.tests.dumpread.exceptions.DumpReadException;

public interface DumpReader {

	public void readDumpIn(InputStream _is) throws DumpReadException;

}