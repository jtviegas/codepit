package org.challenges.rab.statproc;

import org.challenges.rab.statproc.transformers.LineToStatement;
import org.challenges.rab.statproc.validator.StatementValidatorFactory;

public final class StatementProcessorFactory {
  
	public enum FileType {
		CSV, XML
	};

	public StatementProcessor getStatementProcessor(final FileType type) {
		StatementProcessor result = null;
		
		if(type.equals(FileType.XML))
		  result = new XmlStatementProcessor(new StatementValidatorFactory().get());
		else if(type.equals(FileType.CSV))
		  result = new CsvStatementProcessor(new StatementValidatorFactory().get(), new LineToStatement());

		return result;
	}
}
