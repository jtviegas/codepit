package org.aprestos.labs.challenges.glencore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Q {

	@Test
	public void test() throws Exception {
		final Q q = new Q();

		final List<LocalDate> input = Arrays.asList(LocalDate.of(1980, Month.FEBRUARY, 18), // MONDAY
				LocalDate.of(2000, Month.JANUARY, 5), // WEDNESDAY
				LocalDate.of(2017, Month.MAY, 11), // THURSDAY
				LocalDate.of(2017, Month.JANUARY, 3), // TUESDAY
				LocalDate.of(2017, Month.FEBRUARY, 1), // WEDNESDAY
				LocalDate.of(2010, Month.DECEMBER, 23), // THURSDAY
				LocalDate.of(2010, Month.FEBRUARY, 1), // MONDAY
				LocalDate.of(2017, Month.JANUARY, 28), // SATURDAY
				LocalDate.of(2017, Month.JANUARY, 29)); // SUNDAY
		final Map<Month, Long> expected = new HashMap<>();
		expected.put(Month.FEBRUARY, 3L);
		expected.put(Month.JANUARY, 2L);
		expected.put(Month.DECEMBER, 1L);
		expected.put(Month.MAY, 1L);

		Assert.assertEquals(expected, q.countByMonth(input));

	}

	public Map<Month, Long> countByMonth(List<LocalDate> dates) {

		final Map<Month, Long> result = new HashMap<Month, Long>();
		for (LocalDate d : dates) {
			if (d.getDayOfWeek().equals(DayOfWeek.SATURDAY) || d.getDayOfWeek().equals(DayOfWeek.SUNDAY))
				continue;
			Long n = null;
			if (null == (n = result.get(d.getMonth())))
				result.put(d.getMonth(), 1L);
			else
				result.put(d.getMonth(), new Long(n.longValue() + 1));
		}

		return result;
	}

}
