package org.aprestos.labs.tests.caching.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.model.State;

public class DataFeederImpl implements DataFeeder<State>{

	private BufferedReader reader;
	private String line;
	private String keyFormat = "res:%s|ts:%s|metric:%s";

	public DataFeederImpl(File f) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(f));
	}

	public boolean hasNext() throws IOException {
		return (null != (line = reader.readLine()));
	}

	public State next() {
		
		State result = null;
		String key = null;
		String[] fields = null;
		line = line.replaceAll("\\[", "").replaceAll("\\]", "");
		fields = line.split(",");
		key = String.format(keyFormat, fields[7],fields[10].replaceAll("\"", ""),fields[8]);
		
		result = new State(key, fields[9].replaceAll("\"", ""));
	
		return result;
	}

}
