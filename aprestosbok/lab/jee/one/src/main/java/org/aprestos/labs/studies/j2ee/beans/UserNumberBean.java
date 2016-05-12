/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */

package org.aprestos.labs.studies.j2ee.beans;

import java.io.Serializable;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UserNumberBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3822863220026125759L;
	Integer randomInt = null;
	Integer userNumber = null;
	String response = null;
	private long maximum = 10;
	private long minimum = 0;

	public UserNumberBean()
	{
		Random randomGR = new Random();
		randomInt = new Integer(randomGR.nextInt(10));
		System.out.println("Duke's number: " + randomInt);
	}

	public void setUserNumber(Integer user_number)
	{
		userNumber = user_number;
	}

	public Integer getUserNumber()
	{
		return userNumber;
	}

	public String getResponse()
	{
		if ((userNumber != null) && (userNumber.compareTo(randomInt) == 0))
		{
			return "Yay! You got it!";
		} else
		{
			return "Sorry, " + userNumber + " is incorrect.";
		}
	}

	public long getMaximum()
	{
		return (this.maximum);
	}

	public void setMaximum(long maximum)
	{
		this.maximum = maximum;
	}

	public long getMinimum()
	{
		return (this.minimum);
	}

	public void setMinimum(long minimum)
	{
		this.minimum = minimum;
	}
}
