package org.tests.dumpread.parsers.interfaces;

import org.tests.dumpread.data.model.Record;
import org.tests.dumpread.exceptions.DumpReadException;


/**
 * The implementing class shall handle every record line
 * and retrieve the related field values
 * @author joao.viegas
 *
 */
public interface DumpRecordFieldParser 
{
	Record parseRecord(String[] _line) throws DumpReadException;
}
