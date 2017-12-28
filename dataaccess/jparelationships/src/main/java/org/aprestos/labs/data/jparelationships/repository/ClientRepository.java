package org.aprestos.labs.data.jparelationships.repository;

import org.aprestos.labs.data.jparelationships.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
