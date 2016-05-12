/**
 * 
 */
package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author joao.viegas
 *
 */
@Entity
public class Discount implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2657733074631880263L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private double value;
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="discountType_id", nullable=false)
	private DiscountType discountType;
	
	/**
	 * 
	 */
	public Discount()
	{

	}
	
	public Discount(double value, DiscountType type)
	{
		this.value = value;
		this.discountType = type;
	}

	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value)
	{
		this.value = value;
	}

	/**
	 * @return the discountType
	 */
	public DiscountType getDiscountType()
	{
		return discountType;
	}

	/**
	 * @param discountType the discountType to set
	 */
	public void setDiscountType(DiscountType discountType)
	{
		this.discountType = discountType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((discountType == null) ? 0 : discountType.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Discount other = (Discount) obj;
		if (discountType == null)
		{
			if (other.discountType != null)
				return false;
		} else if (!discountType.equals(other.discountType))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
	

}
