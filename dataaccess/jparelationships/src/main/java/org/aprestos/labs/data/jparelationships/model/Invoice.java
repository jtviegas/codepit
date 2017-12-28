package org.aprestos.labs.data.jparelationships.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_seq")
  @SequenceGenerator(name = "invoice_id_seq", sequenceName = "INVOICE_ID_SEQ", allocationSize = 100)
  private Long id;

  private long timestamp;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "invoice")
  private List<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();

  /*
   * @ManyToOne
   * 
   * @JoinColumn(name = "CUSTOMER_ID") private Customer customer;
   */

  public Invoice() {
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

  public List<InvoiceLine> getInvoiceLines() {
    return new ArrayList<InvoiceLine>(invoiceLines);
  }

  public void addInvoiceLine(InvoiceLine invoiceLine) {
    invoiceLine.setInvoice(this);
    this.invoiceLines.add(invoiceLine);
  }

  public void removeInvoiceLine(InvoiceLine invoiceLine) {
    invoiceLine.setInvoice(null);
    this.invoiceLines.remove(invoiceLine);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((invoiceLines == null) ? 0 : invoiceLines.hashCode());
    result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
    Invoice other = (Invoice) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (invoiceLines == null) {
      if (other.invoiceLines != null)
        return false;
    } else if (!invoiceLines.equals(other.invoiceLines))
      return false;
    if (timestamp != other.timestamp)
      return false;
    return true;
  }

  /*
   * public Customer getCustomer() { return customer; }
   * 
   *//**
      * @param customer
      *          the customer to set
      *//*
         * public void setCustomer(Customer customer) { this.customer = customer; }
         */

}
