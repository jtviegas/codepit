package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;



/**
 * Entity implementation class for Entity: Item
 * 
 */
@Entity
public class SaleLineExample implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3913297373122297982L;
	@EmbeddedId
	private SaleLineId id;
	
	private String ean;
	private String description;
	private Double price;
	private Double quantity;
	@Enumerated(EnumType.STRING)
	private MeasureUnit measureUnit;
	/**
	 * @return the id
	 */
	public SaleLineId getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(SaleLineId id)
	{
		this.id = id;
	}
	/**
	 * @return the ean
	 */
	public String getEan()
	{
		return ean;
	}
	/**
	 * @param ean the ean to set
	 */
	public void setEan(String ean)
	{
		this.ean = ean;
	}
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @return the price
	 */
	public Double getPrice()
	{
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price)
	{
		this.price = price;
	}
	/**
	 * @return the quantity
	 */
	public Double getQuantity()
	{
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Double quantity)
	{
		this.quantity = quantity;
	}
	/**
	 * @return the measureUnit
	 */
	public MeasureUnit getMeasureUnit()
	{
		return measureUnit;
	}
	/**
	 * @param measureUnit the measureUnit to set
	 */
	public void setMeasureUnit(MeasureUnit measureUnit)
	{
		this.measureUnit = measureUnit;
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
		result = prime * result + ((ean == null) ? 0 : ean.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((measureUnit == null) ? 0 : measureUnit.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
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
		SaleLineExample other = (SaleLineExample) obj;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (ean == null)
		{
			if (other.ean != null)
				return false;
		} else if (!ean.equals(other.ean))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (measureUnit != other.measureUnit)
			return false;
		if (price == null)
		{
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null)
		{
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	

	
}
