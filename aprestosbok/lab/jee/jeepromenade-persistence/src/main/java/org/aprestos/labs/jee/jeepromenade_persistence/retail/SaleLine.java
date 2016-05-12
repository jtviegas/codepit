package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SaleLine implements Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Discount> discounts = new ArrayList<Discount>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7246093577768854297L;

	public SaleLine()
	{
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
	 * @return the discounts
	 */
	public List<Discount> getDiscounts()
	{
		return discounts;
	}

	/**
	 * @param discounts the discounts to set
	 */
	public void setDiscounts(List<Discount> discounts)
	{
		this.discounts = discounts;
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
				+ ((discounts == null) ? 0 : discounts.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		SaleLine other = (SaleLine) obj;
		if (discounts == null)
		{
			if (other.discounts != null)
				return false;
		} else if (!discounts.equals(other.discounts))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
