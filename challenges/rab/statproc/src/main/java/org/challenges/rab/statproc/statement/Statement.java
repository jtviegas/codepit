package org.challenges.rab.statproc.statement;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Statement {

	private int reference;
	private String accountNumber;
	private String description;
	private BigDecimal startBalance;
	private BigDecimal mutation;
	private BigDecimal endBalance;

	public Statement() {
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getMutation() {
		return mutation;
	}

	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((endBalance == null) ? 0 : endBalance.hashCode());
    result = prime * result + ((mutation == null) ? 0 : mutation.hashCode());
    result = prime * result + reference;
    result = prime * result + ((startBalance == null) ? 0 : startBalance.hashCode());
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
    Statement other = (Statement) obj;
    if (accountNumber == null) {
      if (other.accountNumber != null)
        return false;
    } else if (!accountNumber.equals(other.accountNumber))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (endBalance == null) {
      if (other.endBalance != null)
        return false;
    } else if (!endBalance.equals(other.endBalance))
      return false;
    if (mutation == null) {
      if (other.mutation != null)
        return false;
    } else if (!mutation.equals(other.mutation))
      return false;
    if (reference != other.reference)
      return false;
    if (startBalance == null) {
      if (other.startBalance != null)
        return false;
    } else if (!startBalance.equals(other.startBalance))
      return false;
    return true;
  }


}
