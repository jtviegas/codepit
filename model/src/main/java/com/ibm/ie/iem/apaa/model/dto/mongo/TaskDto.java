package com.ibm.ie.iem.apaa.model.dto.mongo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.NoJackson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@XmlRootElement(name = "task")
public class TaskDto implements Serializable {


	private static final long serialVersionUID = 1L;

	@NoJackson
	@XmlTransient
	@Id
	@GeneratedValue
	@Field(value = "_id")
	private ObjectId objectId;
	private String name;
	private String host;
	private int status;
	private long start;
	private long end;

	public TaskDto() {}

	public TaskDto(String _name,String _host,int _status){
		
			this.name = _name;
			this.host=_host;
			this.status = _status;			
	}

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

	public void setObjectId(ObjectId id) {
		this.objectId = id;
	}

	public String getAccountId() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long creationTimestamp) {
		this.end = creationTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (end ^ (end >>> 32));
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + (int) (start ^ (start >>> 32));
		result = prime * result + status;
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
		TaskDto other = (TaskDto) obj;
		if (end != other.end)
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (start != other.start)
			return false;
		if (status != other.status)
			return false;
		return true;
	}



}
