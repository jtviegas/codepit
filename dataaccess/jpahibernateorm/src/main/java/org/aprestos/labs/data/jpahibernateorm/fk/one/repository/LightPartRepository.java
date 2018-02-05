package org.aprestos.labs.data.jpahibernateorm.fk.one.repository;

import org.aprestos.labs.data.jpahibernateorm.fk.one.model.LightPart;
import org.springframework.data.repository.CrudRepository;

public interface LightPartRepository extends CrudRepository<LightPart, Long> {
}
