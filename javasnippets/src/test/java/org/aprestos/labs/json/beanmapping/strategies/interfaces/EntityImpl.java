package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityImpl implements Entity {

  private static final long serialVersionUID = 1L;

  private String name;

  private String id;

  private boolean on;

  private boolean extrovert;

  @JsonCreator
  public static EntityImpl create(String json) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    EntityImpl module = null;
    module = mapper.readValue(json, EntityImpl.class);
    return module;
  }

  public EntityImpl() {
  }

  @Override
  public @NotNull String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
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
  public @NotNull boolean isOn() {
    return on;
  }

  @Override
  public void setOn(boolean on) {
    this.on = on;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (extrovert ? 1231 : 1237);
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + (on ? 1231 : 1237);
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
    EntityImpl other = (EntityImpl) obj;
    if (extrovert != other.extrovert)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (on != other.on)
      return false;
    return true;
  }

  @Override
  public @NotNull boolean isNotPrivate() {
    return extrovert;
  }

  @Override
  public void setNotPrivate(boolean p) {
    extrovert = p;
  }

}
