package org.aprestos.labs.tests.caching.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.model.ProcessingNode;

public class PNDataFeederLoopImpl implements DataFeeder<ProcessingNode>{

	private int index;
	private int size;

	public PNDataFeederLoopImpl(int size) throws FileNotFoundException {
		this.size = size;
	}

	public boolean hasNext() throws IOException {
		return (index++ < size);
	}

	public ProcessingNode next() {
		return new ProcessingNode(Integer.toString(index - 1));
	}

}
