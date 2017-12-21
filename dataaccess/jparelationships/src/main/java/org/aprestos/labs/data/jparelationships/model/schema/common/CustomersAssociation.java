package org.aprestos.labs.data.jparelationships.model.schema.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class CustomersAssociation {

  @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "core_product_id_seq")
  @SequenceGenerator(name = "core_product_id_seq", schema = "core", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "customersAssociation", fetch = FetchType.EAGER)
  private Set<Customer> customers;

  public CustomersAssociation() {
  }

  public CustomersAssociation(String name) {
    this.name = name;
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
   * @return the customers
   */
  public Set<Customer> getCustomers() {
    if (null == customers)
      customers = new HashSet<Customer>();
    return customers;
  }

  /**
   * @param customers
   *          the customers to set
   */
  public void setCustomers(Set<Customer> customers) {
    this.customers = customers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((customers == null) ? 0 : customers.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CustomersAssociation other = (CustomersAssociation) obj;
    if (customers == null) {
      if (other.customers != null)
        return false;
    } else if (!customers.equals(other.customers))
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
