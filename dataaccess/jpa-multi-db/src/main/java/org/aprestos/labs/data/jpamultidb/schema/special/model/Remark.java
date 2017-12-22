package org.aprestos.labs.data.jpamultidb.schema.special.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.aprestos.labs.data.jpamultidb.schema.common.model.Adjective;

@Entity
@Table(name = "REMARK")
public class Remark implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "TEXT")
  private String text;

  @OneToOne
  @JoinColumn(name = "ADJECTIVE_ID")
  private Adjective adjective;

  public Remark() {
  }

  public Remark(String text, Adjective adjective) {
    this.text = text;
    this.adjective = adjective;
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
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text
   *          the text to set
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return the adjective
   */
  public Adjective getAdjective() {
    return adjective;
  }

  /**
   * @param adjective
   *          the adjective to set
   */
  public void setAdjective(Adjective adjective) {
    this.adjective = adjective;
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
    result = prime * result + ((adjective == null) ? 0 : adjective.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((text == null) ? 0 : text.hashCode());
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
    Remark other = (Remark) obj;
    if (adjective == null) {
      if (other.adjective != null)
        return false;
    } else if (!adjective.equals(other.adjective))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (text == null) {
      if (other.text != null)
        return false;
    } else if (!text.equals(other.text))
      return false;
    return true;
  }

}
