package org.aprestos.labs.ee.domainmodel.notes.dto.mongo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.NoJackson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@XmlRootElement(name = "note")
public class NoteDTO implements Serializable {

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
	private String accountId;
	private String text;
	private int type;
	private int priority;
	private long mustDoTimestamp;
	private long doneTimestamp;
	private long creationTimestamp;

	public NoteDTO() {}

	public NoteDTO(String _accountId,String _text,int _type,
			int _priority,long _mustDoTimestamp){
		
			this.accountId = _accountId;
			this.text=_text;
			this.type = _type;
			this.priority = _priority;
			this.mustDoTimestamp = _mustDoTimestamp;
			
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getMustDoTimestamp() {
		return mustDoTimestamp;
	}

	public void setMustDoTimestamp(long mustDoTimestamp) {
		this.mustDoTimestamp = mustDoTimestamp;
	}

	public long getDoneTimestamp() {
		return doneTimestamp;
	}

	public void setDoneTimestamp(long doneTimestamp) {
		this.doneTimestamp = doneTimestamp;
	}

	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ (int) (creationTimestamp ^ (creationTimestamp >>> 32));
		result = prime * result
				+ (int) (doneTimestamp ^ (doneTimestamp >>> 32));
		result = prime * result
				+ (int) (mustDoTimestamp ^ (mustDoTimestamp >>> 32));
		result = prime * result
				+ ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + priority;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + type;
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
		NoteDTO other = (NoteDTO) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (creationTimestamp != other.creationTimestamp)
			return false;
		if (doneTimestamp != other.doneTimestamp)
			return false;
		if (mustDoTimestamp != other.mustDoTimestamp)
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (priority != other.priority)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type != other.type)
			return false;
		return true;
	}


}
