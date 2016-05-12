package org.aprestos.labs.ee.dataservices.services;

import org.aprestos.labs.ee.domainmodel.auth.Authinfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface AuthinfoService {

	public abstract Iterable<Authinfo> findAll(Sort sort);

	public abstract Page<Authinfo> findAll(Pageable pageable);

	public abstract Authinfo save(Authinfo entity);

	public abstract Iterable<Authinfo> save(Iterable<Authinfo> entities);

	public abstract boolean exists(String id);

	public abstract Iterable<Authinfo> findAll();

	public abstract Iterable<Authinfo> findAll(Iterable<String> ids);

	public abstract long count();

	public abstract void delete(String id);

	public abstract void delete(Authinfo entity);

	public abstract void delete(Iterable<Authinfo> entities);

	public abstract void deleteAll();

	public abstract Authinfo findOne(String id);

}