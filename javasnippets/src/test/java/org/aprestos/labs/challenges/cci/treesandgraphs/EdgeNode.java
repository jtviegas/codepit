package org.aprestos.labs.challenges.cci.treesandgraphs;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class EdgeNode {

	int y;
	int weight;
	EdgeNode next;

	public EdgeNode() {
	}

	public EdgeNode(int y, int weight, EdgeNode next) {
		this.y = y;
		this.weight = weight;
		this.next = next;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
