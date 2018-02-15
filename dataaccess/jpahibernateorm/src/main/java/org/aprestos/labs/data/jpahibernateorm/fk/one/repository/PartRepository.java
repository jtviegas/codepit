package org.aprestos.labs.data.jpahibernateorm.fk.one.repository;

import org.aprestos.labs.data.jpahibernateorm.fk.one.model.Part;
import org.springframework.data.repository.CrudRepository;

public interface PartRepository extends CrudRepository<Part, Long> {
}
