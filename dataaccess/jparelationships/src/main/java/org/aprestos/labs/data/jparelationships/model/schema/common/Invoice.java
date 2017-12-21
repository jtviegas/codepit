package org.aprestos.labs.data.jparelationships.model.schema.common;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Invoice {

  @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
  @SequenceGenerator(name = "product_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
  private Long id;

  private long timestamp;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  public Invoice() {
  }

  public Invoice(Customer c) {
    this.customer = c;
    this.timestamp = (new Date()).getTime();
  }

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
   * @return the timestamp
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp
   *          the timestamp to set
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * @param customer
   *          the customer to set
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
