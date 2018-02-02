package org.aprestos.labs.data.jpahibernateorm.fk.one.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class PartType {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_id_seq")
  @SequenceGenerator(name = "general_id_seq", sequenceName = "GENERAL_ID_SEQ", allocationSize = 100)
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  private String name;

  public PartType() {
  }

  public PartType(String name) {
    this.name = name;
  }

  public PartType(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    PartType other = (PartType) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
