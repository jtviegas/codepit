package org.aprestos.labs.data.jparelationships.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
  @SequenceGenerator(name = "customer_id_seq", sequenceName = "CUSTOMER_ID_SEQ", allocationSize = 100)
  private Long id;

  private String firstName;

  private String lastName;

  @ManyToOne
  // @JoinColumn(name = "customers_association_id")
  @PrimaryKeyJoinColumn
  private CustomersAssociation customersAssociation;

  @OneToMany(targetEntity = Invoice.class, cascade = CascadeType.ALL)
  private Set<Invoice> invoices = new HashSet<Invoice>();

  public Customer() {
  }

  public Customer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
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
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the association
   */
  public CustomersAssociation getAssociation() {
    return customersAssociation;
  }

  /**
   * @param association
   *          the association to set
   */
  public void setAssociation(CustomersAssociation association) {
    this.customersAssociation = association;
  }

  /**
   * @return the customersAssociation
   */
  public CustomersAssociation getCustomersAssociation() {
    return customersAssociation;
  }

  /**
   * @param customersAssociation
   *          the customersAssociation to set
   */
  public void setCustomersAssociation(CustomersAssociation customersAssociation) {
    this.customersAssociation = customersAssociation;
  }

  public Set<Invoice> getInvoices() {
    return invoices;
  }

  /**
   * @param invoices
   *          the invoices to set
   */
  public void setInvoices(Set<Invoice> invoices) {
    this.invoices = invoices;
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
    /* result = prime * result + ((association == null) ? 0 : association.hashCode()); */
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
    Customer other = (Customer) obj;
    /*
     * if (association == null) { if (other.association != null) return false; } else if
     * (!association.equals(other.association)) return false;
     */
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    return true;
  }

}
