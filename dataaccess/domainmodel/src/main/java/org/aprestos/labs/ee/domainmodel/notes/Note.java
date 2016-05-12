package org.aprestos.labs.ee.domainmodel.notes;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "note")
public class Note implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3213994140413103262L;

	private String accountId;
	private String id;
	private String text;
	private int type;
	private int priority;
	private long mustDoTimestamp;
	private long doneTimestamp;
	private long creationTimestamp;
	
	public Note() {}
	
	public Note(String _accountId,String _text,int _type,
		int _priority,long _mustDoTimestamp){
		
		this.accountId = _accountId;
		this.text=_text;
		this.type = _type;
		this.priority = _priority;
		this.mustDoTimestamp = _mustDoTimestamp;
		
	}
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ (int) (mustDoTimestamp ^ (mustDoTimestamp >>> 32));
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
		Note other = (Note) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (creationTimestamp != other.creationTimestamp)
			return false;
		if (doneTimestamp != other.doneTimestamp)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mustDoTimestamp != other.mustDoTimestamp)
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
