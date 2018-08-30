package org.challenges.norcom.indexer.services.bulkfiles;

import java.nio.file.Path;

public interface BulkFilesCreator {

  Path create(Path sourceFolder);

}