package org.aprestos.labs.tests.caching.engines.neo4j.repositories;

import java.util.List;

import org.aprestos.labs.tests.caching.model.ProcessingNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface Neo4jProcessingNodeRepository extends GraphRepository<ProcessingNode> {
	@Query("START pn=node:ProcessingNode(name={0})"+
			"MATCH pn-[:CHILD]-(children) return children")
	List<ProcessingNode> findChildrenByProcessingNodeName(String name);
}
