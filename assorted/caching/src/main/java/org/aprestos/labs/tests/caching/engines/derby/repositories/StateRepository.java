package org.aprestos.labs.tests.caching.engines.derby.repositories;

import org.aprestos.labs.tests.caching.model.State;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface StateRepository extends PagingAndSortingRepository<State, Long> {
	
	public State findByKey(String key);

}
