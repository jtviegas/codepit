package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = EntityImpl.class)
public interface Entity extends NewEntity, Identifiable {

  @JsonProperty("Active")
  @NotNull
  boolean isOn();

  @JsonProperty("Active")
  @NotNull
  void setOn(boolean on);

}
