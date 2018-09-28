package org.challenges.rab.statproc.validator;

import java.util.HashSet;
import java.util.Set;

import org.challenges.rab.statproc.statement.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StatementValidatorImpl implements StatementValidator {

	private Set<Integer> statements;
	private static final Logger logger = LoggerFactory.getLogger(StatementValidatorImpl.class);

	public StatementValidatorImpl() {
		logger.trace("[()|in]");
		statements = new HashSet<Integer>();
		logger.trace("[()|out]");
	}

	@Override
	public boolean validate(Statement s) {
		logger.trace("[validate|in] statement:{}", s);
		boolean r = true;

		if (statements.contains(s.getReference()))
			r = false;
		else {
			statements.add(s.getReference());
			double endBalance = Math.round((s.getStartBalance() + s.getMutation()) * 100.00) / 100.00;
			r &= (0 == Double.compare(endBalance, s.getEndBalance()));
		}

		logger.trace("[validate|out] => {}", r);
		return r;
	}

	@Override
	public void reset() {
		logger.trace("[reset|in]");
		statements.clear();
		logger.trace("[reset|out]");
	}

}
