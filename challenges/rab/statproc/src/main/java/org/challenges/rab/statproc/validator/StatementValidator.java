package org.challenges.rab.statproc.validator;

import org.challenges.rab.statproc.statement.Statement;

public interface StatementValidator {
	boolean validate(Statement s);

	void reset();
}
