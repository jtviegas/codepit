package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Entity extends NewEntity {

  @JsonProperty("id")
  @NotNull
  String getId();

  @JsonProperty("id")
  @NotNull
  void setId(String id);

  @JsonProperty("active")
  @NotNull
  boolean isActive();

  @JsonProperty("active")
  @NotNull
  void setActive(boolean active);

}
