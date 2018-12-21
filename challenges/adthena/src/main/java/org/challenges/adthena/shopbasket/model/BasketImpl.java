package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

class BasketImpl implements Basket {
  
  private final List<BasketItem> items = new ArrayList<BasketItem>();
  
	@Override
	public void addItem(BasketItem item) {
		items.add(item);
	}
	
	private BigDecimal getPromotions() {
    BigDecimal result = BigDecimal.ZERO;
    for( BasketItem i: items )
      result = i.getPromotion().add(result);
    
    return result;
  }
	
	@Override
	public BigDecimal getSubtotal() {
	  BigDecimal result = BigDecimal.ZERO;
		for( BasketItem i: items )
		  result = i.getValue().add(result);
		
		return result;
	}

	@Override
	public BigDecimal getTotal() {
	  BigDecimal promotions =  getPromotions();
		BigDecimal subtotal =  getSubtotal();
		return subtotal.add(promotions);
	}

	@Override
	public List<String> getPromotionDetails() {
		final List<String> result = new ArrayList<String>();
		items.stream().forEach(o -> { 
		  if( null != o.getPromotionText() )
		    result.add(o.getPromotionText());
		});
	  return result;
	}

	@Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((items == null) ? 0 : items.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BasketImpl other = (BasketImpl) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }
	
	
	
}
