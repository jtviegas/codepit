package org.aprestos.labs.tests.caching.engines.hashmap.prevayler;

import java.util.Date;

import org.aprestos.labs.tests.caching.model.State;
import org.prevayler.TransactionWithQuery;

public class CreateStateTrx implements TransactionWithQuery<StateSink, State> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;

	public CreateStateTrx() {}
	
	public CreateStateTrx(String key) {
		this.key = key;
	}

	public State executeAndQuery(StateSink prevalentSystem, Date executionTime) throws Exception {
		State entity = new State();
		entity.setKey(key);
		prevalentSystem.getStates().put(entity.getKey(), entity);
		return entity;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
