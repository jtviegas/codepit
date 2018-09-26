package org.challenges.rab.statproc.validator;

import java.util.HashSet;
import java.util.Set;

import org.challenges.rab.statproc.statement.Statement;

class StatementValidatorImpl implements StatementValidator {

	private Set<Integer> statements;

	public StatementValidatorImpl() {
		statements = new HashSet<Integer>();
	}

	@Override
	public boolean validate(Statement s) {
		boolean r = true;

		if (statements.contains(s.getReference()))
			r = false;
		else {
			statements.add(s.getReference());
			double endBalance = Math.round((s.getStartBalance() + s.getMutation()) * 100.00) / 100.00;
			r &= (0 == Double.compare(endBalance, s.getEndBalance()));
		}

		return r;
	}

	@Override
	public void reset() {
		statements.clear();
	}

}
