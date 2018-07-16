package org.challenges.csloganalyzer.model.transformation;

import org.challenges.csloganalyzer.model.entity.Log;
import org.challenges.csloganalyzer.model.message.LogEntry;

public interface Transformer {

	Log apply(LogEntry e1, LogEntry e2);

}