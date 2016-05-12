/*
 * Copyright  2006-2010. BSkyB Ltd All Rights reserved
 */

package com.sky.dvdstore;

public class Dvd 
{
	
	private String reference;
	private String title;
	private String review;
	
	public Dvd(String reference, String title, String description) 
	{
		this.reference = reference;
		this.title = title;
		this.review = description;
	}

    public String getReview() {
		return review;
	}

	public String getReference() {
		return reference;
	}

	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Dvd)) {
			return false;
		}
		Dvd other = (Dvd) obj;
		if (reference == null) {
			if (other.reference != null) {
				return false;
			}
		} else if (!reference.equals(other.reference)) {
			return false;
		}
		if (review == null) {
			if (other.review != null) {
				return false;
			}
		} else if (!review.equals(other.review)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	
    
}
