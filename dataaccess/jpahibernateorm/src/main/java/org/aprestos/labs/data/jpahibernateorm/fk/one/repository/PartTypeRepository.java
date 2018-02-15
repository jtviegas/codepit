package org.aprestos.labs.data.jpahibernateorm.fk.one.repository;

import org.aprestos.labs.data.jpahibernateorm.fk.one.model.PartType;
import org.springframework.data.repository.CrudRepository;

public interface PartTypeRepository extends CrudRepository<PartType, Long> {
}
