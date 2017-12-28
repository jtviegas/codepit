package org.aprestos.labs.data.jparelationships.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class InvoiceLine {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_line_id_seq")
  @SequenceGenerator(name = "invoice_line_id_seq", sequenceName = "INVOICE_LINE_ID_SEQ", allocationSize = 100)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  private Integer qty;

  public InvoiceLine() {

  }

  public InvoiceLine(Integer qty) {
    this.qty = qty;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Invoice getInvoice() {
    return invoice;
  }

  public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
    result = prime * result + ((qty == null) ? 0 : qty.hashCode());
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
    InvoiceLine other = (InvoiceLine) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;

    if (invoice == null) {
      if (other.invoice != null)
        return false;
    } else if (!invoice.equals(other.invoice))
      return false;

    if (qty == null) {
      if (other.qty != null)
        return false;
    } else if (!qty.equals(other.qty))
      return false;
    return true;
  }

}
