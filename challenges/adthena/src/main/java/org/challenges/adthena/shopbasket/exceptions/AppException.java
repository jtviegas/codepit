package org.challenges.adthena.shopbasket.exceptions;

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppException() {
		super();
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message, Throwable cause, boolean enableSuppression, boolean wStackTrace) {
		super(message, cause, enableSuppression, wStackTrace);
	}

}