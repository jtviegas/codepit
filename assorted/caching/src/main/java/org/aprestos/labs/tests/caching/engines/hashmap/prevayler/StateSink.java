package org.aprestos.labs.tests.caching.engines.hashmap.prevayler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.aprestos.labs.tests.caching.model.State;

public class StateSink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,State> states = new HashMap<String,State>();
	
	public Map<String, State> getStates() {
		return states;
	}
	public void setStates(Map<String, State> states) {
		this.states = states;
	}
	

}
