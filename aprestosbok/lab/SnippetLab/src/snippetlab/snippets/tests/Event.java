package snippetlab.snippets.tests;

import java.util.Date;
import java.util.List;

public class Event
{

	private String name;
	private String city;
	private int editionNumber;
	private Date date;
	private List guestList;
	
	public Event()
	{
		
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}

	public String getCity()
	{
		return city;
	}


	public void setCity(String city)
	{
		this.city = city;
	}


	public int getEditionNumber()
	{
		return editionNumber;
	}


	public void setEditionNumber(int editionNumber)
	{
		this.editionNumber = editionNumber;
	}


	public Date getDate()
	{
		return date;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}


	public List getGuestList()
	{
		return guestList;
	}


	public void setGuestList(List guestList)
	{
		this.guestList = guestList;
	}


	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + editionNumber;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (city == null)
		{
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (date == null)
		{
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (editionNumber != other.editionNumber)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
