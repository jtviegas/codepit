package org.aprestos.labs.tests.caching.engines.neo4j;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.aprestos.labs.tests.caching.engines.neo4j.repositories.Neo4jProcessingNodeRepository;
import org.aprestos.labs.tests.caching.interfaces.IGraphCache;
import org.aprestos.labs.tests.caching.model.ProcessingNode;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("processingNodeCache")
public class Neo4jProcessingNodeCache implements IGraphCache<String, ProcessingNode> {

	@Autowired
	private Neo4jProcessingNodeRepository repository;
	
	public Neo4jProcessingNodeCache() {}

	public void clear() throws CacheException {
		repository.deleteAll();
	}

	public void put(String key, ProcessingNode value) throws CacheException {
		repository.save(value);
	}

	public ProcessingNode get(String name) throws CacheException {
		return repository.findByPropertyValue("name", name);
	}

	public void remove(String name) throws CacheException {
		ProcessingNode pn = null;
		if(null != (pn=repository.findByPropertyValue("name", name)))
			repository.delete(pn);
	}

	public void shutdown() throws CacheException {
		throw new NotImplementedException();
	}

	public List<ProcessingNode> getChildren(String name) throws CacheException {
		return repository.findChildrenByProcessingNodeName(name);
	}

}
