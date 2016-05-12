package org.aprestos.labs.tests.caching.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class ProcessingNode extends AbstractNeo4jEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Indexed(unique=true)
	private String name;
	
	@Fetch
	@RelatedTo(type="CHILD")
	private Set<ProcessingNode> children =  new HashSet<ProcessingNode>();
	
	public String toString(){
		String result = null;
		StringBuffer sb = new StringBuffer();
		for(ProcessingNode pn: children)
			sb.append("," + pn.toString());
		
		result = String.format("ProcessingNode: { name:%s , children: [ %s ]}", name, (0 < sb.length() ? sb.toString().substring(1) : ""));

		return result;
	}
	
	public ProcessingNode() {}
	
	public ProcessingNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProcessingNode> getChildren() {
		return children;
	}

	public void setChildren(Set<ProcessingNode> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessingNode other = (ProcessingNode) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	

}
