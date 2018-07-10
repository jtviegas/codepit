package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = IdentifiableImpl.class)
public interface Identifiable extends Serializable {

  @JsonProperty("Id")
  @NotNull
  String getId();

  @JsonProperty("Id")
  @NotNull
  void setId(String id);

}
