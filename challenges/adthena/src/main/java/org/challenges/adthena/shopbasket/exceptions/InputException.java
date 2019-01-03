package org.challenges.adthena.shopbasket.exceptions;

public class InputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InputException() {
		super();
	}

	public InputException(String arg0) {
		super(arg0);
	}

	public InputException(Throwable arg0) {
		super(arg0);
	}

	public InputException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InputException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
