package org.aprestos.labs.json.beanmapping;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassWithNotNull implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Integer id;
  
  @JsonProperty("feeling")
  private String feeling;

  @NotNull
  @JsonProperty("name")
  private String name;

  public ClassWithNotNull() {
  }
  
  public ClassWithNotNull(Integer id, String name, String feeling) {
    this.id = id;
    this.name= name;
    this.feeling = feeling;
  }

  public String getFeeling() {
    return feeling;
  }

  public void setFeeling(String feeling) {
    this.feeling = feeling;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((feeling == null) ? 0 : feeling.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    ClassWithNotNull other = (ClassWithNotNull) obj;
    if (feeling == null) {
      if (other.feeling != null)
        return false;
    } else if (!feeling.equals(other.feeling))
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
    return true;
  }



}
