package org.aprestos.labs.data.jparelationships.repository;

import org.aprestos.labs.data.jparelationships.model.schema.common.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
