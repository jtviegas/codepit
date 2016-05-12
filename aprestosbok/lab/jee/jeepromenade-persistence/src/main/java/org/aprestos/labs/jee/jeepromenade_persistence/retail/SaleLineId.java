package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Embeddable
public class SaleLineId implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9037314013503058897L;
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long saleId;
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
	 * @return the saleId
	 */
	public long getSaleId()
	{
		return saleId;
	}
	/**
	 * @param saleId the saleId to set
	 */
	public void setSaleId(long saleId)
	{
		this.saleId = saleId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (saleId ^ (saleId >>> 32));
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
		SaleLineId other = (SaleLineId) obj;
		if (id != other.id)
			return false;
		if (saleId != other.saleId)
			return false;
		return true;
	}
	
	
	
}
