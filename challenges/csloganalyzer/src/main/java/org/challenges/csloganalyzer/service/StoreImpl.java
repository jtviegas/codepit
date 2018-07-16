package org.challenges.csloganalyzer.service;

import java.util.Optional;

import org.challenges.csloganalyzer.model.entity.Log;
import org.challenges.csloganalyzer.model.entity.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreImpl implements Store {
	
	private final static Logger logger = LoggerFactory.getLogger(StoreImpl.class);
	
	@Autowired
	private LogRepository logRepository;
	
	@Override
	public void saveLog(Log o) {
		logger.trace("[saveLog|in]");
		logRepository.save(o);
		logger.trace("[saveLog|out]");
	}

	@Override
	public Optional<Log> getLog(String id) {
		return logRepository.findById(id);
	}

	@Override
	public long getLogCount() {
		return logRepository.count();
	}

}
