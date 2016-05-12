package org.aprestos.labs.ee.ws.quote.impl;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.lang.math.RandomUtils;
import org.aprestos.labs.ee.domainmodel.mongo.Quote;
import org.aprestos.labs.ee.mongodatalayer.repositories.QuoteRepository;
import org.aprestos.labs.ee.ws.quote.interfaces.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;


@WebService(endpointInterface="org.aprestos.labs.ee.ws.quote.interfaces.QuoteService", serviceName="QuoteService")
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	QuoteRepository repository;
	
	public int submitQuote(String author, String text) {
		
		Quote quote = new Quote(author,text);
		
		this.repository.save(quote);
		return 0;
	}

	public String[] getRandomQuote() {
		
		String[] result = null;
		Quote quote = null;
		
		List<Quote> collection = (List<Quote>) this.repository.findAll();
		
		int randomIndex = RandomUtils.nextInt(collection.size());
		quote = collection.get(randomIndex);
		
		if(null != quote){
			result = new String[] { quote.getAuthor(), quote.getText() };
		}
		
		return result;
	}

	@Override
	@WebMethod
	public Quote getRandomQuoteObj() {
		
		List<Quote> collection = (List<Quote>) this.repository.findAll();
		
		int randomIndex = RandomUtils.nextInt(collection.size());
		
		return collection.get(randomIndex);
		
	}

}
