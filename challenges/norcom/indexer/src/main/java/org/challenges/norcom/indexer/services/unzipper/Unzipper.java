package org.challenges.norcom.indexer.services.unzipper;

import java.io.IOException;
import java.nio.file.Path;

public interface Unzipper {

	Path unzip(Path file) throws IOException;

}