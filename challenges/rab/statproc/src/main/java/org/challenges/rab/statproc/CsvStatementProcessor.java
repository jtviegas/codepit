package org.challenges.rab.statproc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.exceptions.StatementProcessorException;
import org.challenges.rab.statproc.statement.Statement;
import org.challenges.rab.statproc.validator.StatementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CsvStatementProcessor implements StatementProcessor {

	private static final Logger logger = LoggerFactory.getLogger(CsvStatementProcessor.class);
	private final StatementValidator validator;

	public CsvStatementProcessor(StatementValidator validator) {
		this.validator = validator;
	}

	private Statement fromLine(String line) throws StatementFormatException {

		String[] s = line.split(",");
		Statement o = new Statement();
		try {
			o.setReference(Integer.parseInt(s[0].trim()));
			o.setAccountNumber(s[1].trim());
			o.setDescription(s[2].trim());
			o.setStartBalance(Double.parseDouble(s[3].trim()));
			o.setMutation(Double.parseDouble(s[4].trim()));
			o.setEndBalance(Double.parseDouble(s[5].trim()));
		} catch (Exception e) {
			throw new StatementFormatException(e);
		}
		return o;
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
				Statement statement = fromLine(line);
				if (!validator.validate(statement))
					notvalid.add(String.format("%d,%s", statement.getReference(), statement.getDescription()));
			}

		} catch (IOException e1) {
			throw new StatementProcessorException(e1);
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e2) {
					logger.error("[handle]...when trying to close the reader...", e2);
				}
		}

		String[] r = notvalid.toArray(new String[] {});
		logger.trace("[process|out] r: {}", Arrays.toString(r));
		return r;
	}

}
