package org.challenges.gt.gcodechal.services;

import java.io.IOException;
import java.nio.file.Path;

public interface FileHandler<T> {
	void parse(Path file, Analysis<T> analyser) throws IOException;
}
