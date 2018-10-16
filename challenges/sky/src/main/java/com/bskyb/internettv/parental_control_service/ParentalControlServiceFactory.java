package com.bskyb.internettv.parental_control_service;

import com.bskyb.internettv.thirdparty.MovieService;

public final class ParentalControlServiceFactory {
	public static ParentalControlService getService(MovieService movieService) {
		return new ParentalControlServiceImpl(movieService);
	}
}
