package org.challenges.rab.statproc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.challenges.rab.statproc.exceptions.StatementProcessorException;
import org.challenges.rab.statproc.statement.Statement;
import org.challenges.rab.statproc.transformers.StatementTransformer;
import org.challenges.rab.statproc.validator.StatementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CsvStatementProcessor implements StatementProcessor {

	private static final Logger logger = LoggerFactory.getLogger(CsvStatementProcessor.class);

	private final StatementValidator validator;
	private final StatementTransformer<String> transformer;

	public CsvStatementProcessor(StatementValidator validator, StatementTransformer<String> transformer) {
		logger.trace("[()|in]");
		this.validator = validator;
		this.transformer = transformer;
		logger.trace("[()|out]");
	}

	@Override
	public String[] process(Path file) throws StatementProcessorException {
		logger.trace("[process|in] file: {}", (null != file ? file.toString() : "null"));

		if (null == file)
			throw new IllegalArgumentException("must provide file parameter");

		if (!(file.toFile().exists() && file.toFile().isFile() && file.toFile().canRead()))
			throw new StatementProcessorException(String.format("can't read file: %s", file.toString()));

		List<String> notvalid = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(file);
			String line = null;
			validator.reset();
			reader.readLine();// don't mind the first line
			while ((line = reader.readLine()) != null && 0 < line.length()) {
				logger.trace("[process] processing line: {}", line);
				Statement statement = transformer.toStatement(line);
				if (!validator.validate(statement))
					notvalid.add(String.format("%d,%s", statement.getReference(), statement.getDescription()));

			}

			String[] r = notvalid.toArray(new String[] {});
			logger.info("[process]...finished processing statements. Error report: {}", Arrays.toString(r));
			return r;

		} catch (IOException e1) {
			throw new StatementProcessorException(e1);
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e2) {
					logger.error("[process]...when trying to close the reader...", e2);
				}
			logger.trace("[process|out]");
		}

	}

}
