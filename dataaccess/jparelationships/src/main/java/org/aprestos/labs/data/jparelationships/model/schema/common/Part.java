package org.aprestos.labs.data.jparelationships.model.schema.common;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Part {

  @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
  @SequenceGenerator(name = "product_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
  private Long id;

  private String name;

  private BigDecimal price;

}
