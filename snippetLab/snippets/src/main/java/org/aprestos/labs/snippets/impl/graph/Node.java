package org.aprestos.labs.snippets.impl.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private String id;
	private List<Node> children = new ArrayList<Node>();
	
	public Node(){
	}


	public Node( String id){
		this.id = id;
	}
	
	public Node( String id, int impl){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<Node> getChildren(){
		return new ArrayList<Node>(children);
	}
	
	public void addChildren(List<Node> children){
		this.children.addAll(children);
	}
	
	public void addChild(Node child){
		this.children.add(child);
	}
	
	public void removeChild(Node child){
		this.children.remove(child);
	}
	
	public Node findChild(Node child){
		Node result = null;	
		if(!this.equals(child)){
			for(Node n:children)
				if(null != (result = n.findChild(child)))
					break;
		}
		else
			result = this;

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Node other = (Node) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString(){
		String result = null;
		StringBuffer sb = new StringBuffer();
		for(Node pn: children)
			sb.append("," + pn.toString());
		
		result = String.format("Node: { name:%s , children: [ %s ]}", id, (0 < sb.length() ? sb.toString().substring(1) : ""));

		return result;
	}
	
}
