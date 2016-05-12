package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;

public class SaleItemKey  implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long sale;
	private long itemId;
	/**
	 * @return the sale
	 */
	public long getSale()
	{
		return sale;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale(long sale)
	{
		this.sale = sale;
	}
	/**
	 * @return the item
	 */
	public long getItemId()
	{
		return itemId;
	}
	/**
	 * @param item the item to set
	 */
	public void setItemId(long itemId)
	{
		this.itemId = itemId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		result = prime * result + (int) (sale ^ (sale >>> 32));
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
		SaleItemKey other = (SaleItemKey) obj;
		if (itemId != other.itemId)
			return false;
		if (sale != other.sale)
			return false;
		return true;
	}
	
	
	
}
