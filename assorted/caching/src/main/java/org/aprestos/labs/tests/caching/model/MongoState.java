package org.aprestos.labs.tests.caching.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.NoJackson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@XmlRootElement(name = "state")
public class MongoState implements Serializable {

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
	private String key;
	private String value;

	public MongoState() {}

	public MongoState(String key,String value){
		
			this.key = key;
			this.value=value;
			
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
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
		MongoState other = (MongoState) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	




}
