package org.challenges.norcom.restclient;

public class Id {

	public static Id create(String id) {
		return new Id(id);
	}

	public static Id create(Long id) {
		return new Id(id);
	}

	private String asString;

	private Long asLong;

	private Id(Long o) {
		asLong = o;
		asString = Long.toString(o);
	}

	private Id(String o) {
		asString = o;

		try {
			asLong = Long.parseLong(o);
		} catch (NumberFormatException e) {
		}
	}

	public boolean isString() {
		return null != asString;
	}

	public boolean isLong() {
		return null != asLong;
	}

	public String asString() {
		return asString;
	}

	public Long asLong() {
		return asLong;
	}

	@Override
	public String toString() {
		return asString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asLong == null) ? 0 : asLong.hashCode());
		result = prime * result + ((asString == null) ? 0 : asString.hashCode());
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
		Id other = (Id) obj;
		if (asLong == null) {
			if (other.asLong != null)
				return false;
		} else if (!asLong.equals(other.asLong))
			return false;
		if (asString == null) {
			if (other.asString != null)
				return false;
		} else if (!asString.equals(other.asString))
			return false;
		return true;
	}

}
