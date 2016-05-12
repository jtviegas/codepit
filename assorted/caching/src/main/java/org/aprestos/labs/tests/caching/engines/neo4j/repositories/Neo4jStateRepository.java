package org.aprestos.labs.tests.caching.engines.neo4j.repositories;

import org.aprestos.labs.tests.caching.model.Neo4jState;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface Neo4jStateRepository extends GraphRepository<Neo4jState> {
	Neo4jState findByKey(String key);
}
