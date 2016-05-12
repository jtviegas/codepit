package org.aprestos.labs.ee.datarepositories;

import java.util.Iterator;

import org.aprestos.labs.ee.datarepositories.config.Config;
import org.aprestos.labs.ee.datarepositories.mongo.QuoteMongoRepository;
import org.aprestos.labs.ee.domainmodel.quotes.dto.mongo.QuoteDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Unit test for simple App.
 */
public class QuoteMongoRepositoryIntegrationTest {

	private static final int NUM_OF_ADDITIONS = 2;
	
	AnnotationConfigApplicationContext context;

	@Test
	public void testDocSizeTwoAdditionsToDB() {
		
		
		QuoteMongoRepository repository = (QuoteMongoRepository) context
				.getBean(QuoteMongoRepository.class);
		
		Iterator<QuoteDTO> it = repository.findAll().iterator();
		int initialCount = countIteratorSize(it);
		
		for(int i = 0 ; i < NUM_OF_ADDITIONS ; i++)
			repository.save(new QuoteDTO("jonas", "ola ri lo lela"));
		
		it = repository.findAll().iterator();
		int finalCount = countIteratorSize(it);
		
		Assert.assertEquals(initialCount + NUM_OF_ADDITIONS, finalCount);

	}
	
	
	@Test
	public void testDocSaved() {
		
		QuoteMongoRepository rep = (QuoteMongoRepository) context
				.getBean(QuoteMongoRepository.class);
		QuoteDTO quoteExpected = rep.save(new QuoteDTO("jonas", "ola ri lo lela"));
		QuoteDTO quoteActual = rep.findOne(quoteExpected.getObjectId());
		Assert.assertEquals(quoteExpected, quoteActual);

	}

	/*
	 * (non-Javadoc)M
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext();
		context.register(Config.class);
		context.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		context = null;
	}
	
	private int countIteratorSize( Iterator<?> it){
		int result = 0;
		
		while(it.hasNext()){
			it.next();
			result++;
		}
		
		return result;
	}
	

}
