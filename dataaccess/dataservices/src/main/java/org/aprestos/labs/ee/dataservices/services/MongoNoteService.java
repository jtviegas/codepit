/**
 * 
 */
package org.aprestos.labs.ee.dataservices.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import org.aprestos.labs.ee.datarepositories.mongo.NoteMongoRepository;
import org.aprestos.labs.ee.domainmodel.notes.Note;
import org.aprestos.labs.ee.domainmodel.notes.dto.mongo.NoteDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author joao
 * 
 */
@Service
public class MongoNoteService implements NoteService {

	@Autowired
	private NoteMongoRepository noteMongoRepository;
	private BoundMapperFacade<NoteDTO, Note> mapper;

	class NoteConverter extends BidirectionalConverter<NoteDTO,Note> {

		@Override
		public Note convertTo(NoteDTO source, Type<Note> destinationType) {
			Note o = new Note();
			o.setId(source.getId());
			o.setText(source.getText());
			o.setAccountId(source.getAccountId());
			o.setCreationTimestamp(source.getCreationTimestamp());
			o.setDoneTimestamp(source.getDoneTimestamp());
			o.setMustDoTimestamp(source.getMustDoTimestamp());
			o.setPriority(source.getPriority());
			o.setType(source.getType());
			
			return o;
		}

		@Override
		public NoteDTO convertFrom(Note source, Type<NoteDTO> destinationType) {
			
			NoteDTO o = new NoteDTO();
			
			if(null != source.getId())
				o.setObjectId(new ObjectId(source.getId()));
			
			o.setText(source.getText());
			o.setAccountId(source.getAccountId());
			o.setCreationTimestamp(source.getCreationTimestamp());
			o.setDoneTimestamp(source.getDoneTimestamp());
			o.setMustDoTimestamp(source.getMustDoTimestamp());
			o.setPriority(source.getPriority());
			o.setType(source.getType());
			
			return o;
		}
		
	}
	
	public MongoNoteService() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory.registerConverter(new NoteConverter());
		
		mapperFactory.classMap(NoteDTO.class, Note.class).byDefault()

				.register();
		mapper = mapperFactory.getMapperFacade(NoteDTO.class, Note.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(org.
	 * springframework.data.domain.Sort)
	 */

	public Iterable<Note> findAll(Sort sort) {
		return dto2model(noteMongoRepository.findAll(sort));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(org.
	 * springframework.data.domain.Pageable)
	 */

	public Page<Note> findAll(Pageable pageable) {
		Iterator<NoteDTO> it = noteMongoRepository.findAll(pageable)
				.iterator();
		List<Note> list = new ArrayList<Note>();
		while (it.hasNext())
			list.add(dto2model(it.next()));

		return new PageImpl<Note>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#save(S)
	 */

	public Note save(Note entity) {
		return dto2model(noteMongoRepository.save(model2dto(entity)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#save(java.lang.Iterable
	 * )
	 */

	public Iterable<Note> save(Iterable<Note> entities) {
		return dto2model(noteMongoRepository.save(model2dto(entities)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#exists(org.bson.
	 * types.ObjectId)
	 */

	public boolean exists(String id) {
		return noteMongoRepository.exists(new ObjectId(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll()
	 */

	public Iterable<Note> findAll() {
		return dto2model(noteMongoRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(java.lang
	 * .Iterable)
	 */

	public Iterable<Note> findAll(Iterable<String> ids) {
		return dto2model(noteMongoRepository.findAll(id2ObjectId(ids)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#count()
	 */

	public long count() {
		return noteMongoRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(org.bson.
	 * types.ObjectId)
	 */

	public void delete(String id) {
		noteMongoRepository.delete(new ObjectId(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(org.aprestos
	 * .labs.ee.domainmodel.quotes.Quote)
	 */

	public void delete(Note entity) {
		noteMongoRepository.delete(model2dto(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(java.lang
	 * .Iterable)
	 */

	public void delete(Iterable<Note> entities) {
		noteMongoRepository.delete(model2dto(entities));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#deleteAll()
	 */

	public void deleteAll() {
		noteMongoRepository.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#findOne(org.bson
	 * .types.ObjectId)
	 */

	public Note findOne(String id) {
		return dto2model(noteMongoRepository.findOne(new ObjectId(id)));
	}

	protected Object clone() throws CloneNotSupportedException {
		return new CloneNotSupportedException();
	}

	Iterable<NoteDTO> model2dto(Iterable<Note> ita) {

		Iterator<Note> it = ita.iterator();
		List<NoteDTO> list = new ArrayList<NoteDTO>();
		while (it.hasNext())
			list.add(model2dto(it.next()));

		return list;
	}

	Iterable<Note> dto2model(Iterable<NoteDTO> ita) {
		Iterator<NoteDTO> it = ita.iterator();
		List<Note> list = new ArrayList<Note>();
		while (it.hasNext())
			list.add(dto2model(it.next()));

		return list;
	}
	
	Note dto2model(NoteDTO o) {
		return mapper.map(o);
	}
	
	NoteDTO model2dto(Note o) {
		return mapper.mapReverse(o);
	}

	Iterable<ObjectId> id2ObjectId(Iterable<String> ids) {

		List<ObjectId> objids = new ArrayList<ObjectId>();
		while (ids.iterator().hasNext())
			objids.add(new ObjectId(ids.iterator().next()));

		return objids;
	}

}
