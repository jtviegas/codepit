package org.aprestos.labs.ee.dataservices.services;

import java.util.Iterator;

import org.aprestos.labs.ee.dataservices.config.Config;
import org.aprestos.labs.ee.domainmodel.quotes.Quote;
import org.aprestos.labs.ee.domainmodel.quotes.dto.mongo.QuoteDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Unit test for simple App.
 */
public class MongoQuoteServiceIntegrationTest {

	private static final int NUM_OF_ADDITIONS = 2;
	
	AnnotationConfigApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext();
		context.register(Config.class);
		context.refresh();
	}

	@After
	public void tearDown() throws Exception {
		context = null;
	}
	
	@Test
	public void testGetService() {
		QuoteService service = (QuoteService) context
				.getBean("mongoQuoteService");
		Assert.assertNotNull(service);
	}
	
	@Test
	public void testMappingsOne() {
		MongoQuoteService service = (MongoQuoteService) context
				.getBean("mongoQuoteService");
		Quote model = new Quote("jonas", "ola ri lo lela");
		QuoteDTO dto = new QuoteDTO("jonas", "ola ri lo lela");
		
		Assert.assertEquals(dto, service.model2dto(model));
		Assert.assertEquals(model, service.dto2model(dto));

	}
	
	@Test
	public void testDocSizeTwoAdditionsToDB() {
		
		
		QuoteService service = (QuoteService) context
				.getBean("mongoQuoteService");
		
		Iterator<Quote> it = service.findAll().iterator();
		int initialCount = countIteratorSize(it);
		
		for(int i = 0 ; i < NUM_OF_ADDITIONS ; i++)
			service.save(new Quote("jonas", "ola ri lo lela"));
		
		it = service.findAll().iterator();
		int finalCount = countIteratorSize(it);
		
		Assert.assertEquals(initialCount + NUM_OF_ADDITIONS, finalCount);

	}
	
	
	
	@Test
	public void testDocSaved() {
		
		QuoteService service = (QuoteService) context
				.getBean("mongoQuoteService");
		Quote quoteExpected = service.save(new Quote("jonas", "ola ri lo lela"));
		Quote quoteActual = service.findOne(quoteExpected.getId());
		Assert.assertEquals(quoteExpected, quoteActual);

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
