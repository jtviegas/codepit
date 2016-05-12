package org.aprestos.labs.tests.caching.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.model.State;

public class StateDataFeederLoopImpl implements DataFeeder<State>{

	private String keyFormat = "res:%s|ts:%s|metric:%s";
	
	private Random random;
	private int index;
	private int size;

	public StateDataFeederLoopImpl(int size) throws FileNotFoundException {
		random = new Random();
		this.size = size;
	}

	public boolean hasNext() throws IOException {
		return (index++ < size);
	}

	public State next() {
		
		State result = null;
		String key = null;
		
		int value = random.nextInt(size);
		key = String.format(keyFormat, StateDataFeederLoopImpl.class.getName(),
				Long.toString(System.currentTimeMillis()),
				"metric");
	
		result = new State(key, Integer.toString(value));
	
		return result;
	}

}
