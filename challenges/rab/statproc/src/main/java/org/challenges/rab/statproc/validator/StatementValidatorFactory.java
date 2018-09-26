package org.challenges.rab.statproc.validator;

public final class StatementValidatorFactory {

	public static StatementValidator get() {
		return new StatementValidatorImpl();
	}

}
