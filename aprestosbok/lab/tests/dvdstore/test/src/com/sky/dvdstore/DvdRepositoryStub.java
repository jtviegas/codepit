/*
 * Copyright  2006-2010. BSkyB Ltd All Rights reserved
 */
package com.sky.dvdstore;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

@Ignore
public class DvdRepositoryStub implements DvdRepository {

	// private static final String DVD_REFERENCE_PREFIX = "DVD-";
	//
	// private static final String DVD_TOPGUN_REFERENCE = DVD_REFERENCE_PREFIX +
	// "TG423";
	// private static final String DVD_DIRTYDANCING_REFERENCE =
	// DVD_REFERENCE_PREFIX + "DD878";
	// private static final String DVD_SHREK_REFERENCE = DVD_REFERENCE_PREFIX +
	// "S765";

	private Map<String, Dvd> dvds;

	// static {
	// dvds = new HashMap<String, Dvd>();
	// dvds.put(DVD_TOPGUN_REFERENCE, new Dvd(DVD_TOPGUN_REFERENCE, "Topgun",
	// "All action film"));
	// dvds.put(DVD_DIRTYDANCING_REFERENCE, new Dvd(DVD_DIRTYDANCING_REFERENCE,
	// "Dirty Dancing", "Nobody leaves baby in the corner"));
	// dvds.put(DVD_SHREK_REFERENCE, new Dvd(DVD_SHREK_REFERENCE, "Shrek",
	// "Big green monsters, they're just all " +
	// "the rage these days, what with Hulk, Yoda, and that big ugly troll " +
	// "thingy out of the first Harry Potter movie. But Shrek, the flatulent " +
	// "swamp-dwelling ogre with a heart of gold (well, silver at least), " +
	// "is more than capable of rivalling any of them."));
	// }

	public DvdRepositoryStub() throws Exception {
		loadTestData();
	}

	public Dvd retrieveDvd(String reference) {

		return dvds.get(reference);
	}

	private void loadTestData() throws Exception {
		LineNumberReader _lnr = null;
		String _line = null;
		String _data[] = null;

		try {

			dvds = new HashMap<String, Dvd>();
			_lnr = new LineNumberReader(new InputStreamReader(
					Tests.class.getResourceAsStream(DvdRepositoryStub.class
							.getSimpleName() + ".data")));

			while ((_line = _lnr.readLine()) != null && _line.length() > 0) {
				_data = _line.split("\\|");
				dvds.put(_data[0],
						new Dvd(_data[0].trim(), _data[1].trim(),
								(0 == _data[2].trim().length() ? null
										: _data[2].trim())));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (null != _lnr)
				_lnr.close();

			_lnr = null;
			_data = null;
			_line = null;
		}

	}

}
