package org.aprestos.labs.ee.domainmodel.quotes.dto.mongo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.NoJackson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@XmlRootElement(name = "quote")
public class QuoteDTO implements Serializable {

	/**
	   * 
	   */

	private static final long serialVersionUID = 1L;

	@NoJackson
	@XmlTransient
	@Id
	@GeneratedValue
	@Field(value = "_id")
	private ObjectId objectId;
	private String author;
	private String text;

	public QuoteDTO() {
	}

	public QuoteDTO(String author, String text) {
		this.author = author;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.domainmodel.impl.Quote#getId()
	 */
	public String getId() {
		if (null != objectId)
				return objectId.toString();
		else
			return null;
	}

	public void setId(String id) {
		this.objectId = new ObjectId(id);
	}
	@NoJackson
	@XmlTransient
	public ObjectId getObjectId() {
		return objectId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.domainmodel.impl.Quote#setId(org.bson.types.ObjectId
	 * )
	 */

	public void setObjectId(ObjectId id) {
		this.objectId = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.domainmodel.impl.Quote#getAuthor()
	 */
	public String getAuthor() {
		return author;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.domainmodel.impl.Quote#setAuthor(java.lang.String)
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.domainmodel.impl.Quote#getText()
	 */
	public String getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.domainmodel.impl.Quote#setText(java.lang.String)
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof QuoteDTO))
			return false;
		QuoteDTO other = (QuoteDTO) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	

}
