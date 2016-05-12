package com.sky.dvdstore;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sky.dvdstore.exceptions.DvdNotFoundException;

public class Tests {
	private static DvdService service;

	@BeforeClass
	public static void setupData() throws Exception {
		service = new DvdServiceImpl(new DvdRepositoryStub());
	}

	// -------------------------- retrieveDvd tests

	@Test(expected = DvdNotFoundException.class)
	public void retrieveDvd_invalidReferenceTest() throws DvdNotFoundException {
		service.retrieveDvd("(#%/$#");
	}

	@Test(expected = DvdNotFoundException.class)
	public void retrieveDvd_nullReferenceTest() throws DvdNotFoundException {
		service.retrieveDvd(null);

	}

	@Test(expected = DvdNotFoundException.class)
	public void retrieveDvd_notfoundReferenceTest() throws DvdNotFoundException {
		service.retrieveDvd("DVD-999");
	}

	@Test
	public void retrieveDvd_existentReferenceTest1()
			throws DvdNotFoundException {
		Dvd _expected = new Dvd("DVD-TG423", "Topgun", "All action film");
		org.junit.Assert.assertEquals(_expected,
				service.retrieveDvd("DVD-TG423"));
	}

	@Test
	public void retrieveDvd_existentReferenceTest2()
			throws DvdNotFoundException {
		Dvd _expected = new Dvd("DVD-DD878", "Dirty Dancing",
				"Nobody leaves baby in the corner");
		org.junit.Assert.assertEquals(_expected,
				service.retrieveDvd("DVD-DD878"));
	}

	@Test
	public void retrieveDvd_existentReferenceTest3()
			throws DvdNotFoundException {
		Dvd _expected = new Dvd(
				"DVD-S765",
				"Shrek",
				"Big green monsters, they're just all the rage these days, "
						+ "what with Hulk, Yoda, and that big ugly troll thingy out of the first Harry Potter movie. "
						+ "But Shrek, the flatulent swamp-dwelling ogre with a heart of gold (well, silver at least), "
						+ "is more than capable of rivalling any of them.");
		org.junit.Assert.assertEquals(_expected,
				service.retrieveDvd("DVD-S765"));
	}

	// -------------------------- getDvdSummary tests

	@Test(expected = DvdNotFoundException.class)
	public void getDvdSummary_invalidReferenceTest()
			throws DvdNotFoundException {
		String _expected = "[DVD-DD978] Born to run - Nobody runs that fast, we need the downtown train....";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("VD-DD978"));
	}

	@Test(expected = DvdNotFoundException.class)
	public void getDvdSummary_notfoundTest() throws DvdNotFoundException {
		String _expected = "[DVD-DD9345] not born yet - Nobody runs that fast, there is nobody....";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD9345"));
	}

	@Test
	public void getDvdSummary_good1() throws DvdNotFoundException {
		String _expected = "[DVD-TG423] Topgun - All action film...";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-TG423"));
	}

	@Test
	public void getDvdSummary_good2() throws DvdNotFoundException {
		String _expected = "[DVD-DD878] Dirty Dancing - Nobody leaves baby in the corner...";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD878"));
	}

	@Test
	public void getDvdSummary_good3() throws DvdNotFoundException {
		String _expected = "[DVD-S765] Shrek - Big green monsters, they're just all the rage these days,...";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-S765"));
	}

	@Test
	public void getDvdSummary_good4() throws DvdNotFoundException {
		String _expected = "[DVD-DD978] Born to run - Nobody runs that fast, we need the downtown train....";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD978"));
	}

	@Test
	public void getDvdSummary_good5() throws DvdNotFoundException {
		String _expected = "[DVD-DD979] Born to foo - Foo...";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD979"));
	}

	@Test
	public void getDvdSummary_good6() throws DvdNotFoundException {
		String _expected = "[DVD-DD2] 2 - 2...";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD2"));
	}

	@Test
	public void getDvdSummary_good7() throws DvdNotFoundException {
		String _expected = "[DVD-DD111]  - Nobody runs....";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD111"));
	}

	@Test
	public void getDvdSummary_good8() throws DvdNotFoundException {
		String _expected = "[DVD-DD112] No Review - ...";
		org.junit.Assert.assertEquals(_expected,
				service.getDvdSummary("DVD-DD112"));
	}

}