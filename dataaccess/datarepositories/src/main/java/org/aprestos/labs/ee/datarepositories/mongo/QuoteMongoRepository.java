/**
 * 
 */
package org.aprestos.labs.ee.datarepositories.mongo;

import org.aprestos.labs.ee.domainmodel.quotes.dto.mongo.QuoteDTO;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;



/**
 * @author jviegas
 * 
 */
@Component
public interface QuoteMongoRepository extends PagingAndSortingRepository<QuoteDTO, ObjectId> {

	QuoteDTO findOne(ObjectId id);

}
