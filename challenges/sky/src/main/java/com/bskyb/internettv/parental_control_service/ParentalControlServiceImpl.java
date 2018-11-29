package com.bskyb.internettv.parental_control_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bskyb.internettv.thirdparty.MovieService;

class ParentalControlServiceImpl implements ParentalControlService {

	private static final Logger logger = LoggerFactory.getLogger(ParentalControlServiceImpl.class);

	private final MovieService movieService;

	ParentalControlServiceImpl(MovieService movieService) {
		this.movieService = movieService;
	}

	@Override
	public boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws Exception {
		logger.trace("[canWatchMovie | in](level:{}, movie:{})", customerParentalControlLevel, movieId);
		boolean result = false;
		if (null == customerParentalControlLevel || 0 == customerParentalControlLevel.length())
			throw new RuntimeException("should provide customerParentalControlLevel parameter");
		if (null == movieId || 0 == movieId.length())
			throw new RuntimeException("should provide movieId parameter");

		ParentalControlLevels customerLevel = ParentalControlLevels.fromString(customerParentalControlLevel);
		if (null == customerLevel)
			throw new Exception("wrong customerParentalControlLevel provided");

		String level = movieService.getParentalControlLevel(movieId);

		result = ParentalControlLevels.fromString(level).asLevel() <= customerLevel.asLevel();
		logger.trace("[canWatchMovie | out] => {}", result);
		return result;
	}

}
