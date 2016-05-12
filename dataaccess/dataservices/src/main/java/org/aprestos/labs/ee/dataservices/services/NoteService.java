package org.aprestos.labs.ee.dataservices.services;

import org.aprestos.labs.ee.domainmodel.notes.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface NoteService {

	public abstract Iterable<Note> findAll(Sort sort);

	public abstract Page<Note> findAll(Pageable pageable);

	public abstract Note save(Note entity);

	public abstract Iterable<Note> save(Iterable<Note> entities);

	public abstract boolean exists(String id);

	public abstract Iterable<Note> findAll();

	public abstract Iterable<Note> findAll(Iterable<String> ids);

	public abstract long count();

	public abstract void delete(String id);

	public abstract void delete(Note entity);

	public abstract void delete(Iterable<Note> entities);

	public abstract void deleteAll();

	public abstract Note findOne(String id);

}