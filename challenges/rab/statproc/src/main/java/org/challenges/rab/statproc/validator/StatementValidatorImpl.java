package org.challenges.rab.statproc.validator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.challenges.rab.statproc.statement.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * One simple implementation for the StatementValidator interface
 * 
 * @author jtviegas
 *
 */
class StatementValidatorImpl implements StatementValidator {

	private final Set<Integer> statements;
	private static final Logger LOGGER = LoggerFactory.getLogger(StatementValidatorImpl.class);

	public StatementValidatorImpl() {
		LOGGER.trace("[()|in]");
		statements = new HashSet<Integer>();
		LOGGER.trace("[()|out]");
	}

	@Override
	public boolean validate(final Statement statement) {
		LOGGER.trace("[validate|in] statement:{}", statement);
		boolean result = true;
		
		if (!statements.contains(statement.getReference())) {
			statements.add(statement.getReference());
			BigDecimal finalBalance = statement.getStartBalance().add(statement.getMutation());
			result &= 0 == finalBalance.compareTo(statement.getEndBalance());
		}
		else
		  result = false;

		LOGGER.trace("[validate|out] => {}", result);
		return result;
	}

	@Override
	public void reset() {
		LOGGER.trace("[reset|in]");
		statements.clear();
		LOGGER.trace("[reset|out]");
	}

}
