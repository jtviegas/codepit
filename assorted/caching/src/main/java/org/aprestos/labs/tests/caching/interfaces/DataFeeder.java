package org.aprestos.labs.tests.caching.interfaces;

import java.io.IOException;



public interface DataFeeder<E> {

	public boolean hasNext() throws IOException ;

	public E next();

}
