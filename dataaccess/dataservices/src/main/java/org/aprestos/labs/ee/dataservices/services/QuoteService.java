package org.aprestos.labs.ee.dataservices.services;

import org.aprestos.labs.ee.domainmodel.quotes.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface QuoteService {

	public abstract Iterable<Quote> findAll(Sort sort);

	public abstract Page<Quote> findAll(Pageable pageable);

	public abstract Quote save(Quote entity);

	public abstract Iterable<Quote> save(Iterable<Quote> entities);

	public abstract boolean exists(String id);

	public abstract Iterable<Quote> findAll();

	public abstract Iterable<Quote> findAll(Iterable<String> ids);

	public abstract long count();

	public abstract void delete(String id);

	public abstract void delete(Quote entity);

	public abstract void delete(Iterable<Quote> entities);

	public abstract void deleteAll();

	public abstract Quote findOne(String id);

}