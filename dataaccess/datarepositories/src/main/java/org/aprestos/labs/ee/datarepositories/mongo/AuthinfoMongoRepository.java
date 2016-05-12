/**
 * 
 */
package org.aprestos.labs.ee.datarepositories.mongo;

import org.aprestos.labs.ee.domainmodel.auth.dto.mongo.AuthinfoDTO;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;



/**
 * @author jviegas
 * 
 */
@Component
public interface AuthinfoMongoRepository extends PagingAndSortingRepository<AuthinfoDTO, ObjectId> {

	AuthinfoDTO findOne(ObjectId id);

}
