/**
 * 
 */
package org.aprestos.labs.tests.caching.engines.mongo.repositories;

import org.aprestos.labs.tests.caching.model.MongoState;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;



/**
 * @author jviegas
 * 
 */
@Component
public interface StateMongoRepository extends PagingAndSortingRepository<MongoState, ObjectId> {

	MongoState findByKey(String key);
}
