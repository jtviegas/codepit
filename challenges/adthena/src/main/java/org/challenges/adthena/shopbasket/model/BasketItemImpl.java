package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

class BasketItemImpl implements BasketItem {

	private BigDecimal value = BigDecimal.ZERO;
	private String name;
	private String promotionText;
	private BigDecimal promotionValue = BigDecimal.ZERO;

	@Override
	public void setValue(BigDecimal value) {
		this.value= value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPromotion(String text, BigDecimal value) {
		this.promotionText = text;
		this.promotionValue = value;
	}

	@Override
	public BigDecimal getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPromotionText() {
		return promotionText;
	}

	@Override
	public BigDecimal getPromotion() {
		return promotionValue;
	}
	
	@Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((promotionText == null) ? 0 : promotionText.hashCode());
    result = prime * result + ((promotionValue == null) ? 0 : promotionValue.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    BasketItemImpl other = (BasketItemImpl) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (promotionText == null) {
      if (other.promotionText != null)
        return false;
    } else if (!promotionText.equals(other.promotionText))
      return false;
    if (promotionValue == null) {
      if (other.promotionValue != null)
        return false;
    } else if (!promotionValue.equals(other.promotionValue))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }
	

}
