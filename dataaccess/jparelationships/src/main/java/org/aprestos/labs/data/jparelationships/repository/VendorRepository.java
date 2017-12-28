package org.aprestos.labs.data.jparelationships.repository;

import org.aprestos.labs.data.jparelationships.model.Vendor;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
