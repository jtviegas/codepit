package org.challenges.rab.statproc.exceptions;

public class StatementFormatException extends StatementProcessorException {

	private static final long serialVersionUID = 1L;

	public StatementFormatException() {
		super();
	}

	public StatementFormatException(String arg0) {
		super(arg0);
	}

	public StatementFormatException(Throwable arg0) {
		super(arg0);
	}

	public StatementFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public StatementFormatException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
