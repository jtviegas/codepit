package org.aprestos.labs.data.jparelationships.repository;

import java.util.List;

import org.aprestos.labs.data.jparelationships.model.schema.common.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  List<Customer> findByLastName(String lastName);
}
