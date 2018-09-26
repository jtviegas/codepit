package org.challenges.rab.statproc;

import org.challenges.rab.statproc.transformers.LineToStatement;
import org.challenges.rab.statproc.validator.StatementValidatorFactory;

public final class StatementProcessorFactory {

	public static enum FileType {
		CSV, XML
	};

	public static StatementProcessor getStatementProcessor(FileType type) {
		StatementProcessor result = null;

		switch (type) {
		case XML:
			result = new XmlStatementProcessor(StatementValidatorFactory.get());
			break;
		default:
			result = new CsvStatementProcessor(StatementValidatorFactory.get(), new LineToStatement());
		}

		return result;
	}
}
