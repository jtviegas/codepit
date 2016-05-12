/**
 * 
 */
package org.aprestos.labs.ee.dataservices.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import org.aprestos.labs.ee.datarepositories.mongo.QuoteMongoRepository;
import org.aprestos.labs.ee.domainmodel.quotes.Quote;
import org.aprestos.labs.ee.domainmodel.quotes.dto.mongo.QuoteDTO;
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
public class MongoQuoteService implements QuoteService {

	@Autowired
	private QuoteMongoRepository quoteMongoRepository;
	private BoundMapperFacade<QuoteDTO, Quote> mapper;

	class QuoteConverter extends BidirectionalConverter<QuoteDTO,Quote> {

		@Override
		public Quote convertTo(QuoteDTO source, Type<Quote> destinationType) {
			Quote o = new Quote();
			o.setId(source.getId());
			o.setText(source.getText());
			o.setAuthor(source.getAuthor());
			return o;
		}

		@Override
		public QuoteDTO convertFrom(Quote source, Type<QuoteDTO> destinationType) {
			
			QuoteDTO o = new QuoteDTO();
			
			if(null != source.getId())
				o.setObjectId(new ObjectId(source.getId()));
			
			o.setText(source.getText());
			o.setAuthor(source.getAuthor());
			
			return o;
		}
		
	}
	
	public MongoQuoteService() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
				.build();
		ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory.registerConverter(new QuoteConverter());
		
		mapperFactory.classMap(QuoteDTO.class, Quote.class).byDefault()
				/*.customize(new CustomMapper<QuoteDTO,Quote>(){
					@Override
					public void mapAtoB(QuoteDTO a, Quote b,
							MappingContext context) {

						b.setId(a.getId());
						b.setText(a.getText());
						b.setAuthor(a.getAuthor());
						
						
					}
					@Override
					public void mapBtoA(Quote b, QuoteDTO a,
							MappingContext context) {
						
						if(null != b.getId())
							a.setObjectId(new ObjectId(b.getId()));
						
						a.setText(b.getText());
						a.setAuthor(b.getAuthor());
					}
				})*/
				.register();
		mapper = mapperFactory.getMapperFacade(QuoteDTO.class, Quote.class);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(org.
	 * springframework.data.domain.Sort)
	 */

	public Iterable<Quote> findAll(Sort sort) {
		return dto2model(quoteMongoRepository.findAll(sort));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(org.
	 * springframework.data.domain.Pageable)
	 */

	public Page<Quote> findAll(Pageable pageable) {
		Iterator<QuoteDTO> it = quoteMongoRepository.findAll(pageable)
				.iterator();
		List<Quote> list = new ArrayList<Quote>();
		while (it.hasNext())
			list.add(dto2model(it.next()));

		return new PageImpl<Quote>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#save(S)
	 */

	public Quote save(Quote entity) {
		return dto2model(quoteMongoRepository.save(model2dto(entity)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#save(java.lang.Iterable
	 * )
	 */

	public Iterable<Quote> save(Iterable<Quote> entities) {
		return dto2model(quoteMongoRepository.save(model2dto(entities)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#exists(org.bson.
	 * types.ObjectId)
	 */

	public boolean exists(String id) {
		return quoteMongoRepository.exists(new ObjectId(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll()
	 */

	public Iterable<Quote> findAll() {
		return dto2model(quoteMongoRepository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#findAll(java.lang
	 * .Iterable)
	 */

	public Iterable<Quote> findAll(Iterable<String> ids) {
		return dto2model(quoteMongoRepository.findAll(id2ObjectId(ids)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#count()
	 */

	public long count() {
		return quoteMongoRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(org.bson.
	 * types.ObjectId)
	 */

	public void delete(String id) {
		quoteMongoRepository.delete(new ObjectId(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(org.aprestos
	 * .labs.ee.domainmodel.quotes.Quote)
	 */

	public void delete(Quote entity) {
		quoteMongoRepository.delete(model2dto(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#delete(java.lang
	 * .Iterable)
	 */

	public void delete(Iterable<Quote> entities) {
		quoteMongoRepository.delete(model2dto(entities));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aprestos.labs.ee.dataservices.mongo.QuoteService#deleteAll()
	 */

	public void deleteAll() {
		quoteMongoRepository.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aprestos.labs.ee.dataservices.mongo.QuoteService#findOne(org.bson
	 * .types.ObjectId)
	 */

	public Quote findOne(String id) {
		// TODO Auto-generated method stub
		return dto2model(quoteMongoRepository.findOne(new ObjectId(id)));
	}

	protected Object clone() throws CloneNotSupportedException {
		return new CloneNotSupportedException();
	}

	Iterable<QuoteDTO> model2dto(Iterable<Quote> ita) {

		Iterator<Quote> it = ita.iterator();
		List<QuoteDTO> list = new ArrayList<QuoteDTO>();
		while (it.hasNext())
			list.add(model2dto(it.next()));

		return list;
	}

	Iterable<Quote> dto2model(Iterable<QuoteDTO> ita) {
		Iterator<QuoteDTO> it = ita.iterator();
		List<Quote> list = new ArrayList<Quote>();
		while (it.hasNext())
			list.add(dto2model(it.next()));

		return list;
	}
	
	Quote dto2model(QuoteDTO o) {
		return mapper.map(o);
	}
	
	QuoteDTO model2dto(Quote o) {
		return mapper.mapReverse(o);
	}

	Iterable<ObjectId> id2ObjectId(Iterable<String> ids) {

		List<ObjectId> objids = new ArrayList<ObjectId>();
		while (ids.iterator().hasNext())
			objids.add(new ObjectId(ids.iterator().next()));

		return objids;
	}

}
