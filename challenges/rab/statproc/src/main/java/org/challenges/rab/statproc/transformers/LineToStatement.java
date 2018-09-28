package org.challenges.rab.statproc.transformers;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.statement.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineToStatement implements StatementTransformer<String> {

	private static final Logger logger = LoggerFactory.getLogger(LineToStatement.class);

	@Override
	public Statement toStatement(String t) throws StatementFormatException {
		logger.trace("[toStatement|in] line: {}", t);

		String[] s = t.split(",");
		Statement o = new Statement();
		try {
			o.setReference(Integer.parseInt(s[0].trim()));
			o.setAccountNumber(s[1].trim());
			o.setDescription(s[2].trim());
			o.setStartBalance(Double.parseDouble(s[3].trim()));
			o.setMutation(Double.parseDouble(s[4].trim()));
			o.setEndBalance(Double.parseDouble(s[5].trim()));
			return o;
		} catch (Exception e) {
			throw new StatementFormatException(e);
		} finally {
			logger.trace("[toStatement|out] statement: {}", o);
		}

	}

}
