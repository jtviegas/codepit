package org.tests.dumpread.data.interfaces;

import java.util.List;

import org.tests.dumpread.data.model.Record;
import org.tests.dumpread.exceptions.GWException;

public interface RecordGW 
{
	void init() throws GWException;
	void deinit() throws GWException;
	
	void startTransaction() throws GWException;
	void commitTransaction() throws GWException;
	void rollbackTransaction() throws GWException;
	
	void insert(Record _r) throws GWException;
	void insert(List<Record> _rs) throws GWException;
	
	
	
}
