package org.aprestos.labs.data.jpahibernateorm.fk.one.model;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity(name = "Part")
public class LightPart {

  @Basic
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_id_seq")
  @SequenceGenerator(name = "general_id_seq", sequenceName = "GENERAL_ID_SEQ", allocationSize = 100)
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  private String name;

  private BigDecimal price;

  @Column(name = "part_type_id")
  private Long partTypeId;

  public LightPart() {
  };

  public LightPart(String name, BigDecimal price, Long partTypeId) {
    this.name = name;
    this.price = price;
    this.partTypeId = partTypeId;
  };

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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Long getPartTypeId() {
    return partTypeId;
  }

  public void setPartTypeId(Long partTypeId) {
    this.partTypeId = partTypeId;
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
    LightPart other = (LightPart) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
