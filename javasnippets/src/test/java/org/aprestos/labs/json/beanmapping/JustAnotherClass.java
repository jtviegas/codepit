package org.aprestos.labs.json.beanmapping;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JustAnotherClass extends JustAClass {

  private static final long serialVersionUID = 1L;

  @JsonProperty("feeling")
  private String feeling;

  @JsonProperty("gender")
  private Gender gender;

  @JsonProperty("Tough")
  private boolean tough;

  public JustAnotherClass() {
  }

  public String getFeeling() {
    return feeling;
  }

  public void setFeeling(String feeling) {
    this.feeling = feeling;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public boolean isTough() {
    return tough;
  }

  public void setTough(boolean tough) {
    this.tough = tough;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((feeling == null) ? 0 : feeling.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + (tough ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    JustAnotherClass other = (JustAnotherClass) obj;
    if (feeling == null) {
      if (other.feeling != null)
        return false;
    } else if (!feeling.equals(other.feeling))
      return false;
    if (gender != other.gender)
      return false;
    if (tough != other.tough)
      return false;
    return true;
  }

}
