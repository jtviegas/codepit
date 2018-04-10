package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Identifiable extends Serializable {

  @JsonProperty("id")
  @NotNull
  String getId();

  @JsonProperty("id")
  @NotNull
  void setId(String id);
}
