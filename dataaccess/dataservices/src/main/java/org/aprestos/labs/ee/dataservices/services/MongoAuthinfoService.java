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

import org.aprestos.labs.ee.datarepositories.mongo.AuthinfoMongoRepository;
import org.aprestos.labs.ee.domainmodel.auth.Authinfo;
import org.aprestos.labs.ee.domainmodel.auth.dto.mongo.AuthinfoDTO;
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
public class MongoAuthinfoService implements AuthinfoService {

	@Autowired
	private AuthinfoMongoRepository authinfoMongoRepository;
	private BoundMapperFacade<AuthinfoDTO, Authinfo> mapper;

	class AuthinfoConverter extends BidirectionalConverter<AuthinfoDTO,Authinfo> {

		@Override
		public Authinfo convertTo(AuthinfoDTO source, Type<Authinfo> destinationType) {
			
			Authinfo o = new Authinfo();
			
			o.setId(source.getId());
			o.setAccessToken(source.getAccessToken());
			o.setAccountId(source.getAccountId());
			o.setRequestToken(source.getRequestToken());
			o.setSessionId(source.getSessionId());
			
			return o;
		}

		@Override
		public AuthinfoDTO convertFrom(Authinfo source, Type<AuthinfoDTO> destinationType) {
			
			AuthinfoDTO o = new AuthinfoDTO();
			
			if(null != source.getId())
				o.setObjectId(new ObjectId(source.getId()));
			
			o.setAccessToken(source.getAccessToken());
			o.setAccountId(source.getAccountId());
			o.setRequestToken(source.getRequestToken());
			o.setSessionId(source.getSessionId());
			
			return o;
		}
		
	}
	
	public MongoAuthinfoService() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory.registerConverter(new AuthinfoConverter());
		
		mapperFactory.classMap(AuthinfoDTO.class, Authinfo.class).byDefault()

				.register();
		mapper = mapperFactory.getMapperFacade(AuthinfoDTO.class, Authinfo.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(org.
	 * springframework.data.domain.Sort)
	 */

	public Iterable<Authinfo> findAll(Sort sort) {
		return dto2model(authinfoMongoRepository.findAll(sort));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(org.
	 * springframework.data.domain.Pageable)
	 */

	public Page<Authinfo> findAll(Pageable pageable) {
		Iterator<AuthinfoDTO> it = authinfoMongoRepository.findAll(pageable)
				.iterator();
		List<Authinfo> list = new ArrayList<Authinfo>();
		while (it.hasNext())
			list.add(dto2model(it.next()));

		return new PageImpl<Authinfo>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#save(S)
	 */

	public Authinfo save(Authinfo entity) {
		return dto2model(authinfoMongoRepository.save(model2dto(entity)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#save(java.lang.Iterable
	 * )
	 */

	public Iterable<Authinfo> save(Iterable<Authinfo> entities) {
		return dto2model(authinfoMongoRepository.save(model2dto(entities)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#exists(org.bson.
	 * types.ObjectId)
	 */

	public boolean exists(String id) {
		return authinfoMongoRepository.exists(new ObjectId(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll()
	 */

	public Iterable<Authinfo> findAll() {
		return dto2model(authinfoMongoRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(java.lang
	 * .Iterable)
	 */

	public Iterable<Authinfo> findAll(Iterable<String> ids) {
		return dto2model(authinfoMongoRepository.findAll(id2ObjectId(ids)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#count()
	 */

	public long count() {
		return authinfoMongoRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(org.bson.
	 * types.ObjectId)
	 */

	public void delete(String id) {
		authinfoMongoRepository.delete(new ObjectId(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(org.aprestos
	 * .labs.ee.domainmodel.quotes.Quote)
	 */

	public void delete(Authinfo entity) {
		authinfoMongoRepository.delete(model2dto(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(java.lang
	 * .Iterable)
	 */

	public void delete(Iterable<Authinfo> entities) {
		authinfoMongoRepository.delete(model2dto(entities));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#deleteAll()
	 */

	public void deleteAll() {
		authinfoMongoRepository.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#findOne(org.bson
	 * .types.ObjectId)
	 */

	public Authinfo findOne(String id) {
		return dto2model(authinfoMongoRepository.findOne(new ObjectId(id)));
	}

	protected Object clone() throws CloneNotSupportedException {
		return new CloneNotSupportedException();
	}

	Iterable<AuthinfoDTO> model2dto(Iterable<Authinfo> ita) {

		Iterator<Authinfo> it = ita.iterator();
		List<AuthinfoDTO> list = new ArrayList<AuthinfoDTO>();
		while (it.hasNext())
			list.add(model2dto(it.next()));

		return list;
	}

	Iterable<Authinfo> dto2model(Iterable<AuthinfoDTO> ita) {
		Iterator<AuthinfoDTO> it = ita.iterator();
		List<Authinfo> list = new ArrayList<Authinfo>();
		while (it.hasNext())
			list.add(dto2model(it.next()));

		return list;
	}
	
	Authinfo dto2model(AuthinfoDTO o) {
		return mapper.map(o);
	}
	
	AuthinfoDTO model2dto(Authinfo o) {
		return mapper.mapReverse(o);
	}

	Iterable<ObjectId> id2ObjectId(Iterable<String> ids) {

		List<ObjectId> objids = new ArrayList<ObjectId>();
		while (ids.iterator().hasNext())
			objids.add(new ObjectId(ids.iterator().next()));

		return objids;
	}

}
