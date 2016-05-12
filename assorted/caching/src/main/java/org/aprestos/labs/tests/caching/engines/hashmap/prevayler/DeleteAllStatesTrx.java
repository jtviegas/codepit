package org.aprestos.labs.tests.caching.engines.hashmap.prevayler;

import java.util.Date;

import org.prevayler.Transaction;

public class DeleteAllStatesTrx implements Transaction<StateSink> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeleteAllStatesTrx() {}

	public void executeOn(StateSink prevalentSystem, Date executionTime) {
		prevalentSystem.getStates().clear();
	}


}
