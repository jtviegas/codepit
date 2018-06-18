package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GuyImpl implements Guy {

  @JsonCreator
  public static GuyImpl create(String json) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    GuyImpl module = null;
    module = mapper.readValue(json, GuyImpl.class);
    return module;
  }

  private static final long serialVersionUID = 1L;

  private String n;

  private String i;

  private boolean a;

  public GuyImpl() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aprestos.labs.json.beanmapping.strategies.interfaces.IGuy#getName()
   */
  @Override
  public @NotNull String getName() {
    return n;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aprestos.labs.json.beanmapping.strategies.interfaces.IGuy#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {
    this.n = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aprestos.labs.json.beanmapping.strategies.interfaces.IGuy#getId()
   */
  @Override
  public @NotNull String getId() {
    return i;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aprestos.labs.json.beanmapping.strategies.interfaces.IGuy#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    this.i = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aprestos.labs.json.beanmapping.strategies.interfaces.IGuy#isActive()
   */
  @Override
  public @NotNull boolean isOn() {
    return a;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aprestos.labs.json.beanmapping.strategies.interfaces.IGuy#setActive(boolean)
   */
  @Override
  public void setOn(boolean active) {
    this.a = active;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (a ? 1231 : 1237);
    result = prime * result + ((i == null) ? 0 : i.hashCode());
    result = prime * result + ((n == null) ? 0 : n.hashCode());
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
    GuyImpl other = (GuyImpl) obj;
    if (a != other.a)
      return false;
    if (i == null) {
      if (other.i != null)
        return false;
    } else if (!i.equals(other.i))
      return false;
    if (n == null) {
      if (other.n != null)
        return false;
    } else if (!n.equals(other.n))
      return false;
    return true;
  }

  @Override
  public @NotNull boolean isNotPrivate() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setNotPrivate(boolean p) {
    // TODO Auto-generated method stub

  }

}
