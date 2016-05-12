package org.aprestos.labs.tests.caching.engines.hashmap.prevayler;

import java.util.Date;

import org.aprestos.labs.tests.caching.model.State;
import org.prevayler.TransactionWithQuery;

public class DeleteStateTrx implements TransactionWithQuery<StateSink, State> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	
	public DeleteStateTrx() {}
	
	public DeleteStateTrx(String key) {
		this.key = key;
	}

	public State executeAndQuery(StateSink prevalentSystem, Date executionTime) throws Exception {
		return prevalentSystem.getStates().remove(this.key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
