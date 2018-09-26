package org.challenges.rab.statproc;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.challenges.rab.statproc.exceptions.StatementProcessorException;

public interface StatementProcessor {
	String[] process(Path file) throws StatementProcessorException, FileNotFoundException;
}
