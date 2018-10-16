package com.bskyb.internettv.parental_control_service;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParentalControlServiceTest {

	@Mock
	private MovieService movieService;

	private ParentalControlService service;

	@Before
	public void setup() {
		service = ParentalControlServiceFactory.getService(movieService);
	}

	@Test
	public void test_lessRestrictiveMovie() throws Exception {

		String movieId = "movieU";
		when(movieService.getParentalControlLevel(movieId)).thenReturn("PG");
		Assert.assertTrue(service.canWatchMovie("12", movieId));
	}

	@Test
	public void test_lessRestrictiveMovie2() throws Exception {
		String movieId = "movie15";
		when(movieService.getParentalControlLevel(movieId)).thenReturn("15");
		Assert.assertTrue(service.canWatchMovie("18", movieId));
	}

	@Test
	public void test_equallyRestrictiveMovie() throws Exception {
		String movieId = "movie12";
		when(movieService.getParentalControlLevel(movieId)).thenReturn("12");
		Assert.assertTrue(service.canWatchMovie("12", movieId));
	}

	@Test
	public void test_equallyRestrictiveMovie2() throws Exception {
		String movieId = "movieU";
		when(movieService.getParentalControlLevel(movieId)).thenReturn("U");
		Assert.assertTrue(service.canWatchMovie("U", movieId));
	}

	@Test
	public void test_moreRestrictiveMovie() throws Exception {
		String movieId = "movie18";
		when(movieService.getParentalControlLevel(movieId)).thenReturn("18");
		Assert.assertFalse(service.canWatchMovie("12", movieId));
	}

	@Test
	public void test_moreRestrictiveMovie2() throws Exception {
		String movieId = "movie12";
		when(movieService.getParentalControlLevel(movieId)).thenReturn("12");
		Assert.assertFalse(service.canWatchMovie("PG", movieId));
	}

	@Test(expected = Exception.class)
	public void test_movieNotFound() throws Exception {
		String movieId = "unknown";
		when(movieService.getParentalControlLevel(movieId)).thenThrow(new TitleNotFoundException());
		service.canWatchMovie("PG", movieId);
		Assert.fail();
	}

	@Test(expected = Exception.class)
	public void test_failure() throws Exception {
		String movieId = "failuremovie";
		when(movieService.getParentalControlLevel(movieId)).thenThrow(new TechnicalFailureException());
		service.canWatchMovie("PG", movieId);
		Assert.fail();
	}

	@Test(expected = RuntimeException.class)
	public void test_noLevel() throws Exception {
		String movieId = "failuremovie";
		service.canWatchMovie("", movieId);
		Assert.fail();
	}

	@Test(expected = RuntimeException.class)
	public void test_noLevel2() throws Exception {
		String movieId = "failuremovie";
		service.canWatchMovie(null, movieId);
		Assert.fail();
	}

	@Test(expected = RuntimeException.class)
	public void test_noMovieParam() throws Exception {
		String movieId = "";
		service.canWatchMovie("PG", movieId);
		Assert.fail();
	}

	@Test(expected = RuntimeException.class)
	public void test_noMovieParam2() throws Exception {
		service.canWatchMovie("PG", null);
		Assert.fail();
	}

	@Test(expected = Exception.class)
	public void test_wrongLevel() throws Exception {
		String movieId = "movie";
		service.canWatchMovie("PTG", movieId);
		Assert.fail();
	}

}
