package org.challenges.norcom.indexer.tasks;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

public class FolderProcessor implements Function<Path, Map<String,Object>> {

  private FileProcessor fileProcessor = new FileProcessor();
  @Override
  public Map<String, Object> apply(Path t) {
    
    Files.walk(t).map(o -> o.toFile().isDirectory() ? apply(o) : fileProcessor.apply(o)).flatMap(mapper);
    // TODO Auto-generated method stub
    return null;
    
  }

}
