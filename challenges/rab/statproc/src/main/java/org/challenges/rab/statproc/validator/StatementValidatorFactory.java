package org.challenges.rab.statproc.validator;

public final class StatementValidatorFactory {
  
	public StatementValidator get() {
		return new StatementValidatorImpl();
	}

}
