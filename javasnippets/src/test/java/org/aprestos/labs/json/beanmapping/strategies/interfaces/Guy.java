package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import javax.validation.constraints.NotNull;

public class Guy implements Entity, Identifiable {

  private static final long serialVersionUID = 1L;

  private String n;

  private String i;

  private boolean a;

  public Guy() {
  }

  public Guy(String name, String id, boolean active) {
    this.n = name;
    this.i = id;
    this.a = active;
  }

  @Override
  public @NotNull String getName() {
    return n;
  }

  @Override
  public void setName(String name) {
    this.n = name;
  }

  @Override
  public @NotNull String getId() {
    return i;
  }

  @Override
  public void setId(String id) {
    this.i = id;
  }

  @Override
  public @NotNull boolean isActive() {
    return a;
  }

  @Override
  public void setActive(boolean active) {
    this.a = active;
  }

}
