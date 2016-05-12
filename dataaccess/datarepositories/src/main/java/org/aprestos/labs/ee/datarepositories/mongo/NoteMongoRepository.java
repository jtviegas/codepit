/**
 * 
 */
package org.aprestos.labs.ee.datarepositories.mongo;

import org.aprestos.labs.ee.domainmodel.notes.dto.mongo.NoteDTO;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;



/**
 * @author jviegas
 * 
 */
@Component
public interface NoteMongoRepository extends PagingAndSortingRepository<NoteDTO, ObjectId> {

	NoteDTO findOne(ObjectId id);

}
