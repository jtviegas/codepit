package org.challenges.rab.statproc.transformers;

import java.math.BigDecimal;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.statement.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineToStatement implements StatementTransformer<String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LineToStatement.class);

	@Override
	public Statement toStatement(final String statementLine) throws StatementFormatException {
		LOGGER.trace("[toStatement|in] line: {}", statementLine);

		String[] statementParts = statementLine.split(",");
		Statement statement = new Statement();
		try {
			statement.setReference(Integer.parseInt(statementParts[0].trim()));
			statement.setAccountNumber(statementParts[1].trim());
			statement.setDescription(statementParts[2].trim());
			
			statement.setStartBalance(new BigDecimal(statementParts[3].trim()));
			statement.setMutation(new BigDecimal(statementParts[4].trim()));
			statement.setEndBalance(new BigDecimal(statementParts[5].trim()));
			return statement;
		} catch (Exception exception) {
			throw new StatementFormatException(exception);
		} finally {
			LOGGER.trace("[toStatement|out] statement: {}", statement);
		}

	}

}
