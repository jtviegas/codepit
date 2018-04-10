package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface NewEntity extends Serializable {

  @JsonProperty("name")
  @NotNull
  String getName();

  @JsonProperty("name")
  @NotNull
  void setName(String name);
}
