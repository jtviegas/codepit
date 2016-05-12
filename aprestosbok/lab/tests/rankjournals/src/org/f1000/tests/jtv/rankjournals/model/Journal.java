package org.f1000.tests.jtv.rankjournals.model;

public class Journal
{
	public enum CATEGORY{ GENERAL,REVIEW,ECONOMICS,ENTERTAINMENT }
	
	private float score;
	private String name;
	private CATEGORY category = CATEGORY.GENERAL; //default category is general
	
	public Journal(){}
	
	public Journal(String name, float score)
	{
		this.name = name;
		this.score = score;
	}

	public Journal(String name, float score, CATEGORY category)
	{
		this.name = name;
		this.score = score; 
		this.category = category;
	}
	
	/**
	 * @return the score
	 */
	public float getScore()
	{
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(float score)
	{
		this.score = score;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the category
	 */
	public CATEGORY getCategory()
	{
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(CATEGORY category)
	{
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(score);
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
		if (!(obj instanceof Journal)) {
			return false;
		}
		Journal other = (Journal) obj;
		if (category != other.category) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score)) {
			return false;
		}
		return true;
	}

	
	
	

}
