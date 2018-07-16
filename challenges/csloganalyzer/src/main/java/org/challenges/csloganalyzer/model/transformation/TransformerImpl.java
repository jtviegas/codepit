package org.challenges.csloganalyzer.model.transformation;

import java.util.function.BiFunction;

import org.challenges.csloganalyzer.model.entity.Log;
import org.challenges.csloganalyzer.model.message.LogEntry;
import org.challenges.csloganalyzer.model.message.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransformerImpl implements BiFunction<LogEntry, LogEntry, Log>, Transformer {

	private static final Logger logger = LoggerFactory.getLogger(TransformerImpl.class);
	private static final long ALERT_THRESHOLD = 4l;

	@Override
	public Log apply(LogEntry e1, LogEntry e2) {
		logger.debug("[apply|in] {}", new Object[] { e1, e2 });
		Log r = new Log();

		r.setId(e1.getId());
		r.setType(e1.getType());
		if (null != e1.getHost())
			r.setHost(e1.getHost());

		if (e1.getState().equals(State.STARTED))
			r.setDuration(e2.getTimestamp() - e1.getTimestamp());
		else
			r.setDuration(e1.getTimestamp() - e2.getTimestamp());

		if (r.getDuration() > ALERT_THRESHOLD)
			r.setAlert(Boolean.TRUE);

		logger.debug("[apply|out] {}", r);
		return r;
	}

}
