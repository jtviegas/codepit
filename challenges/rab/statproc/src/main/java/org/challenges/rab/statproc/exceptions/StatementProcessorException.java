package org.challenges.rab.statproc.exceptions;

public class StatementProcessorException extends Exception {

	private static final long serialVersionUID = 1L;

	public StatementProcessorException() {
	}

	public StatementProcessorException(String arg0) {
		super(arg0);
	}

	public StatementProcessorException(Throwable arg0) {
		super(arg0);
	}

	public StatementProcessorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public StatementProcessorException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
