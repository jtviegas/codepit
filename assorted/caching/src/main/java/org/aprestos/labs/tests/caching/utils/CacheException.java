package org.aprestos.labs.tests.caching.utils;

public class CacheException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CacheException() {
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
