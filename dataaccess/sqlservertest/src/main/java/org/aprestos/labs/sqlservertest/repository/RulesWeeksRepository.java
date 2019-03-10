package org.aprestos.labs.sqlservertest.repository;

import org.aprestos.labs.sqlservertest.model.RulesWeeks;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface RulesWeeksRepository extends CrudRepository<RulesWeeks, BigDecimal> {
}
