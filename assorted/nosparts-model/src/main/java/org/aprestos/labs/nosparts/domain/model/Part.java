package org.aprestos.labs.nosparts.domain.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Part {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "subtype", referencedColumnName = "id")
  private Subtype subtype;

  private String name;

  private String description;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] image;

  private double price;

  @OneToMany
  /* (mappedBy = "part") */
  private List<Comment> comments;

  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * @return the subtype
   */
  public Subtype getSubtype() {
    return subtype;
  }

  /**
   * @param subtype
   *          the subtype to set
   */
  public void setSubtype(Subtype subtype) {
    this.subtype = subtype;
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
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the image
   */
  public byte[] getImage() {
    return image;
  }

  /**
   * @param image
   *          the image to set
   */
  public void setImage(byte[] image) {
    this.image = image;
  }

  /**
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param price
   *          the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * @return the comments
   */
  public List<Comment> getComments() {
    return comments;
  }

  /**
   * @param comments
   *          the comments to set
   */
  public void setComments(List<Comment> comments) {
    this.comments = comments;
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
    result = prime * result + ((comments == null) ? 0 : comments.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + Arrays.hashCode(image);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    long temp;
    temp = Double.doubleToLongBits(price);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((subtype == null) ? 0 : subtype.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Part)) {
      return false;
    }
    Part other = (Part) obj;
    if (comments == null) {
      if (other.comments != null) {
        return false;
      }
    } else if (!comments.equals(other.comments)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (!Arrays.equals(image, other.image)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) {
      return false;
    }
    if (subtype == null) {
      if (other.subtype != null) {
        return false;
      }
    } else if (!subtype.equals(other.subtype)) {
      return false;
    }
    return true;
  }

}
