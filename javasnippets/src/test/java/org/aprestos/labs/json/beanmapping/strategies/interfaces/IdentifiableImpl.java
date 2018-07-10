package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IdentifiableImpl implements Identifiable {

  private static final long serialVersionUID = 1L;

  private String id;

  public IdentifiableImpl() {
  }

  @Override
  public @NotNull String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    IdentifiableImpl other = (IdentifiableImpl) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @JsonCreator
  public static IdentifiableImpl create(String json) throws JsonParseException, JsonMappingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    IdentifiableImpl module = null;
    module = mapper.readValue(json, IdentifiableImpl.class);
    return module;
  }

}
