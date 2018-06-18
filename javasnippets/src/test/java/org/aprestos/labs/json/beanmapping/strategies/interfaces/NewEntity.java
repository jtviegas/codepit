package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = NewEntityImpl.class)
public interface NewEntity extends Serializable {

  @JsonProperty("Name")
  @NotNull
  String getName();

  @JsonProperty("Name")
  void setName(String name);

  @JsonProperty("Public")
  @NotNull
  boolean isNotPrivate();

  @JsonProperty("Public")
  void setNotPrivate(boolean p);

}
