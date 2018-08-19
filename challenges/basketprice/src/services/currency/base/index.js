"use strict";

const axios = require('axios');
const logger = require('../../common').logger;
const config = require('../../../config');

var CurrencyService = function(){

	var getRate = function(fromCur, toCur, callback){
        logger.debug('[CurrencyService.getRate] IN');
        try {
        	checkCurrencies(fromCur, toCur);
        	
        	if( toCur === fromCur )
        		callback(null, 1.00);
        	else {
        		var localCallback = function(cb){
        			var f = function(e, rate){
        				cb(e, rate);
        			};
        			return {f:f};
        		}(callback);
        		retrieveRate ( fromCur, toCur, localCallback.f);
        	}
        		
        }
        catch(e){
        	callback(e);
        }
        finally {
        	logger.debug('[CurrencyService.getRate] OUT');	
        }
        
    };
    
    var checkCurrencies = function(src, end){
    	if( -1 == config.services.currency.currencies.from.indexOf(src.toUpperCase()) )
    	 throw new SourceCurrencyNotSupportedError(src.toUpperCase());
    	if( -1 == config.services.currency.currencies.to.indexOf(end.toUpperCase()) )
       	 throw new EndCurrencyNotSupportedError(end.toUpperCase());
    }
    
    var retrieveRate = function(fromCur, toCur, callback){
    	logger.debug('[CurrencyService.retrieveRate] IN');
    	let endpoint = config.services.currency.api + '&currencies=' + toCur + '&source=' + fromCur;
    	console.log('endpoint', endpoint)
    	axios.get(endpoint) 
    	.then(o => {
    		logger.debug('[CurrencyService.retrieveRate.callback] response:', o.data.quotes);
    		let key = fromCur + toCur;
    		if( o.data.quotes[key] )
    			callback(null, o.data.quotes[key]);
    		else
    			callback(new Error("could not retrive exchange rate"));
    	  })
    	  .catch(error => {
    		  callback(error);
    	  });
        logger.debug('[CurrencyService.retrieveRate] OUT');
    }

    return {
        getRate: getRate
    };

}();

module.exports = CurrencyService;
