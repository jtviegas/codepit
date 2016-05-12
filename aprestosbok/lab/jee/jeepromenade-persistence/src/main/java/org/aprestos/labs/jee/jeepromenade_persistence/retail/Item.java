package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;



/**
 * Entity implementation class for Entity: Item
 * 
 */
@IdClass(org.aprestos.labs.jee.jeepromenade_persistence.retail.SaleItemKey.class)
@Entity
public class Item implements Serializable
{

	private long itemId;
	private String description;
	private Double price;
	private Sale sale;
	
	private static final long serialVersionUID = 1L;

	public Item(){}
	
	public Item(String _desc, double _price)
	{
		this.description = _desc;
		this.price = _price;
	}
	
	public Item(String _desc, double _price, Sale _sale)
	{
		this.description = _desc;
		this.price = _price;
		this.sale = _sale;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getItemId()
	{
		return this.itemId;
	}

	public void setItemId(long id)
	{
		this.itemId = id;
	}

	@NotNull
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@NotNull
	public Double getPrice()
	{
		return this.price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	@Id
	@ManyToOne
	@JoinColumn(name="saleId")
	public Sale getSale()
	{
		return this.sale;
	}
	
	
	public void setSale(Sale _sale)
	{
		this.sale = _sale;
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
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((sale == null) ? 0 : sale.hashCode());
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
		Item other = (Item) obj;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (itemId != other.itemId)
			return false;
		if (price == null)
		{
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (sale == null)
		{
			if (other.sale != null)
				return false;
		} else if (!sale.equals(other.sale))
			return false;
		return true;
	}

	
	
}
