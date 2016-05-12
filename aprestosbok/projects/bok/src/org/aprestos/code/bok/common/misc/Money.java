/**
 * Money.java
 * copyright aprestos.org, 2008.
 */
package org.aprestos.code.bok.common.misc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

/**
 * 
 */
public class Money implements Comparable<Money>
{

    private long amount;
    private Currency currency;
    private RoundingMode roundingMode = RoundingMode.HALF_UP;
    /**
     * 
     */
    public Money(){};
    
    public Money(long amount, Currency currency)
    {
	this.currency = currency;
	this.amount = amount * centFactor();
    }
    
    public Money(double amount, Currency currency)
    {
	this.currency = currency;
	this.amount = Math.round(amount * centFactor());
    }
    
    public Money(long amount, Currency currency, RoundingMode rm)
    {
	this(amount,currency);
	this.roundingMode = rm;
    }
    
    public Money(double amount, Currency currency, RoundingMode rm)
    {
	this(amount,currency);
	this.roundingMode = rm;
    }

    public static Money dollars(double amount)
    {
	return new Money(amount, Currency.getInstance(Locale.US));
    }
    
    public static Money euros(double amount)
    {
	return new Money(amount, Currency.getInstance(Locale.GERMANY));
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Money o)
    {
	assertSameCurrencyAs(o);
	if(amount < o.amount)
	    return -1;
	else if(amount == o.amount)
	    return 0;
	else
	    return 1;
    }

    public Money multiply(BigDecimal factor)
    {
	MathContext mc = new MathContext(currency.getDefaultFractionDigits(),roundingMode);
	return new Money(amount().multiply(factor,mc).doubleValue(), currency);
    }
    
    public Money divide(BigDecimal factor)
    {
	MathContext mc = new MathContext(currency.getDefaultFractionDigits(),roundingMode);
	return new Money(amount().divide(factor,mc).doubleValue(), currency);
    }
    
    public Money add(Money other)
    {
	assertSameCurrencyAs(other);
	return newMoney(amount + other.amount);
    }
    
    public Money subtract(Money other)
    {
	assertSameCurrencyAs(other);
	return newMoney(amount - other.amount);
    }
    
    public Money[] allocate(int n)
    {
	Money lowResult = newMoney(amount / n);
	Money highResult = newMoney(lowResult.amount + 1);
	Money[] results = new Money[n];
	int remainder = (int) amount % n;
	
	for(int i = 0; i < remainder ; i++)results[i]=highResult;
	for(int i = remainder; i < n ; i++)results[i]=lowResult;
	
	return results;
    }

    public BigDecimal amount()
    {
	return BigDecimal.valueOf(this.amount, currency.getDefaultFractionDigits());
    }
    
    public Currency currency()
    {
	return this.currency;
    }
    
    private final static int[] cents = new int[]{1,10,100,1000};
    
    private int centFactor()
    {
	return cents[currency.getDefaultFractionDigits()];
    }

    private void assertSameCurrencyAs(Money arg)
    {
	assert currency.equals(arg.currency()):"money match mysmatch";
    }
    
    private Money newMoney(long amount)
    {
	Money money=new Money();
	money.currency = this.currency;
	money.amount = amount;
	money.roundingMode = this.roundingMode;
	return money;
    }
    
}
