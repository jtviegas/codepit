package org.aprestos.labs.data.jparelationships.repository;

import org.aprestos.labs.data.jparelationships.model.schema.common.CustomersAssociation;
import org.springframework.data.repository.CrudRepository;

public interface CustomersAssociationRepository extends CrudRepository<CustomersAssociation, Long> {

}
