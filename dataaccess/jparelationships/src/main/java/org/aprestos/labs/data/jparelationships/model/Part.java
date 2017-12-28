package org.aprestos.labs.data.jparelationships.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Part {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_id_seq")
  @SequenceGenerator(name = "part_id_seq", sequenceName = "PART_ID_SEQ", allocationSize = 100)
  private Long id;

  private String name;

  private BigDecimal price;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the price
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * @param price
   *          the price to set
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
