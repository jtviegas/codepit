package org.challenges.gt.gcodechal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Line2AddressBookEntry implements Function<String, AddressBookEntry> {

	private static final Logger LOGGER = LoggerFactory.getLogger(Line2AddressBookEntry.class);
	private static final int TERMS_IN_LINE = 3;
	private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder().appendPattern("dd/MM/")
			.optionalStart().appendPattern("uuuu").optionalEnd().optionalStart()
			.appendValueReduced(ChronoField.YEAR, 2, 2, 1920).optionalEnd().toFormatter();

	@Override
	public AddressBookEntry apply(final String line) {
		LOGGER.trace("[apply|in] {}", line);
		final String[] terms = line.split(",");
		if (TERMS_IN_LINE != terms.length)
			throw new IllegalStateException(String.format("wrong line in csv addressbook file [line: {}]", line));

		try {
			final AddressBookEntry result = new AddressBookEntry();
			final String name = terms[0].trim();
			if (0 == name.length())
				throw new IllegalStateException(String.format("empty name in csv addressbook entry [line: {}]", line));
			result.setName(name);
			final Gender gender = Gender.fromString(terms[1].trim());
			if (null == gender)
				throw new IllegalStateException(
						String.format("wrong gender in csv addressbook entry [line: {}]", line));
			result.setGender(gender);

			final String dateAsStr = terms[2].trim();
			if (0 == dateAsStr.length())
				throw new IllegalStateException(
						String.format("empty date of birth in csv addressbook entry [line: {}]", line));

			result.setDateOfBirth(LocalDate.parse(dateAsStr, DATE_FORMATTER));
			return result;
		} catch (IllegalStateException ise) {
			throw ise;
		} catch (Exception e) {
			throw new IllegalStateException(String.format("could not parse line: {}", line), e);
		} finally {
			LOGGER.trace("[apply|out]");
		}

	}

}
