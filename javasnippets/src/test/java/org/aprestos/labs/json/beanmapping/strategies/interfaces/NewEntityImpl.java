package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NewEntityImpl implements NewEntity {

  private static final long serialVersionUID = 1L;

  private String name;

  private boolean notPrivate;

  public NewEntityImpl() {
  }

  @Override
  public @NotNull String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @JsonCreator
  public static NewEntityImpl create(String json) throws JsonParseException, JsonMappingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    NewEntityImpl module = null;
    module = mapper.readValue(json, NewEntityImpl.class);
    return module;
  }

  @Override
  public @NotNull boolean isNotPrivate() {
    return notPrivate;
  }

  @Override
  public void setNotPrivate(boolean p) {
    this.notPrivate = p;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + (notPrivate ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NewEntityImpl other = (NewEntityImpl) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (notPrivate != other.notPrivate)
      return false;
    return true;
  }

}
