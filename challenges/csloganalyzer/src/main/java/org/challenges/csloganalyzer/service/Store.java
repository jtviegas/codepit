package org.challenges.csloganalyzer.service;

import java.util.Optional;

import org.challenges.csloganalyzer.model.entity.Log;

public interface Store {
	void saveLog(Log o);
	Optional<Log> getLog(String id);
	long getLogCount();
}
