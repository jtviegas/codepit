package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FriendImpl implements Friend {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String degree;

  private Guy guy;

  public FriendImpl() {
  }

  @Override
  public String getDegree() {
    return degree;
  }

  @Override
  public void setDegree(String d) {
    this.degree = d;
  }

  @Override
  public Guy getGuy() {
    return guy;
  }

  @Override
  public void setGuy(Guy g) {
    this.guy = g;
  }

  @JsonCreator
  public static FriendImpl create(String json) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    FriendImpl module = null;
    module = mapper.readValue(json, FriendImpl.class);
    return module;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((degree == null) ? 0 : degree.hashCode());
    result = prime * result + ((guy == null) ? 0 : guy.hashCode());
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
    FriendImpl other = (FriendImpl) obj;
    if (degree == null) {
      if (other.degree != null)
        return false;
    } else if (!degree.equals(other.degree))
      return false;
    if (guy == null) {
      if (other.guy != null)
        return false;
    } else if (!guy.equals(other.guy))
      return false;
    return true;
  }

}
