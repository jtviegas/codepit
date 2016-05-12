package org.aprestos.labs.tests.caching.engines.hashmap.prevayler;

import java.util.Date;

import org.aprestos.labs.tests.caching.model.State;
import org.prevayler.Query;

public class GetState implements Query<StateSink, State> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	
	public GetState() {}
	
	public GetState(String key) {
		this.key = key;
	}

	public State query(StateSink prevalentSystem, Date executionTime) throws Exception {
		return prevalentSystem.getStates().get(this.key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
