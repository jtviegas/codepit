package org.challenges.gt.gcodechal.services;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.challenges.gt.gcodechal.model.AddressBookEntry;
import org.challenges.gt.gcodechal.model.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnalysisImpl implements Analysis<AddressBookEntry> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisImpl.class);
	private static final String OUT_PATTERNS[] = new String[] { "How many males are in the address book? %d",
			"Who is the oldest person in the address book? %s", "How many days older is Bill than Paul? %d" };
	private static final String PAUL_NAME = "paul", BILL_NAME = "bill";

	private int numMales;
	private long ageDiffInDaysBillPaul;
	private AddressBookEntry oldest;
	private LocalDate birthdayPaul, birthdayBill;

	@Override
	public void analyse(final AddressBookEntry entry) {
		LOGGER.trace("[analyse|in] {}", entry.toString());
		if (entry.getGender().equals(Gender.Male))
			numMales++;

		if (null == oldest) {
			oldest = entry;
		} else if (oldest.getDateOfBirth().isAfter(entry.getDateOfBirth())) {
			oldest = entry;
		}

		if (entry.getName().toLowerCase(Locale.getDefault()).startsWith(PAUL_NAME))
			birthdayPaul = entry.getDateOfBirth();

		if (entry.getName().toLowerCase(Locale.getDefault()).startsWith(BILL_NAME))
			birthdayBill = entry.getDateOfBirth();
		LOGGER.trace("[analyse|out]");
	}

	@Override
	public void printOutcome(final OutputStream outStream) {
		LOGGER.trace("[printOutcome|in]");
		final PrintStream printStream = new PrintStream(outStream);
		printStream.println(String.format(OUT_PATTERNS[0], numMales));
		printStream.println(String.format(OUT_PATTERNS[1], null != oldest ? oldest.getName() : "null"));
		if (null != birthdayBill && null != birthdayPaul) {
			ageDiffInDaysBillPaul = birthdayBill.until(birthdayPaul, ChronoUnit.DAYS);
		}
		printStream.println(String.format(OUT_PATTERNS[2], ageDiffInDaysBillPaul));
		LOGGER.trace("[printOutcome|out]");
	}

	@Override
	public void reset() {
		LOGGER.trace("[reset|in]");
		numMales = 0;
		ageDiffInDaysBillPaul = 0;
		oldest = null;
		birthdayPaul = null;
		birthdayBill = null;
		LOGGER.trace("[reset|out]");
	}

}
