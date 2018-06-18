package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = FriendImpl.class)
public interface Friend extends Serializable {

  @JsonProperty("Degree")
  @NotNull
  String getDegree();

  @JsonProperty("Degree")
  @NotNull
  void setDegree(String d);

  @JsonProperty("Guy")
  @NotNull
  Guy getGuy();

  @JsonProperty("Guy")
  @NotNull
  void setGuy(Guy g);
}
