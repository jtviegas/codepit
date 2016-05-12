package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * Entity implementation class for Entity: Sale
 * 
 */
@Entity
@NamedQuery(name = "getSales", query = "SELECT s FROM Sale s")
public class Sale implements Serializable
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long saleId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private List<SaleLineExample> items = new ArrayList<SaleLineExample>();
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
	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
	/**
	 * @return the items
	 */
	public List<SaleLineExample> getItems()
	{
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<SaleLineExample> items)
	{
		this.items = items;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		Sale other = (Sale) obj;
		if (date == null)
		{
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (items == null)
		{
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (saleId != other.saleId)
			return false;
		return true;
	}


	
}
