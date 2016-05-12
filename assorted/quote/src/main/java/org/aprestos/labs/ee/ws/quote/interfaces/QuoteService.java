package org.aprestos.labs.ee.ws.quote.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.aprestos.labs.ee.domainmodel.mongo.Quote;

@WebService
public interface QuoteService {
	
	@WebMethod
	public int submitQuote(String author, String text);
	
	@WebMethod
	public String[] getRandomQuote();
	
	@WebMethod
	public Quote getRandomQuoteObj();
	
}
