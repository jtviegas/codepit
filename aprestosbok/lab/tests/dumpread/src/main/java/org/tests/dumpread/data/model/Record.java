package org.tests.dumpread.data.model;

public class Record 
{
	private int id;
	private int articleId;
	private String attribute;
	private String value;
	private short language;
	private short type;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the articleId
	 */
	public int getArticleId() {
		return articleId;
	}
	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
	 * @return the text
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param text the text to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the language
	 */
	public short getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(short language) {
		this.language = language;
	}
	/**
	 * @return the type
	 */
	public short getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(short type) {
		this.type = type;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + articleId;
		result = prime * result
				+ ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + id;
		result = prime * result + language;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + type;
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
		if (!(obj instanceof Record)) {
			return false;
		}
		Record other = (Record) obj;
		if (articleId != other.articleId) {
			return false;
		}
		if (attribute == null) {
			if (other.attribute != null) {
				return false;
			}
		} else if (!attribute.equals(other.attribute)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (language != other.language) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
	
	
	

}
