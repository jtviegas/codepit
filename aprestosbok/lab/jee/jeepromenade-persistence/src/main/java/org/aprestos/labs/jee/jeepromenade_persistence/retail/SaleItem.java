package org.aprestos.labs.jee.jeepromenade_persistence.retail;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SaleItem implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7773370725748154772L;
	private long id;
	private String ean;
	private String unitOfMeasure;
	private int quantity;
	private String description;
	private double price;
	
	/**
	 * @return the id
	 */
	@Id
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
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure()
	{
		return unitOfMeasure;
	}
	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure)
	{
		this.unitOfMeasure = unitOfMeasure;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity()
	{
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
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
	public double getPrice()
	{
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	
}
