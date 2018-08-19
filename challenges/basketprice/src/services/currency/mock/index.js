"use strict";

var logger = require('../../common').logger;
var config = require('../../../config');
const SourceCurrencyNotSupportedError = require('../../../representations/errors').SourceCurrencyNotSupportedError;
const EndCurrencyNotSupportedError = require('../../../representations/errors').EndCurrencyNotSupportedError;

var MockCurrencyService = function(){


    var getRate = function(fromCur, toCur, callback){
        logger.debug('[MockCurrencyService.getRate] IN');
        try {
        	checkCurrencies(fromCur, toCur);
        	if( toCur === fromCur )
        		callback(null, 1.00);
        	else
        		callback(null, 1.07);
        }
        catch(e){
        	callback(e);
        }
        finally {
        	logger.debug('[MockCurrencyService.getRate] OUT');	
        }
        
    };
    
    var checkCurrencies = function(src, end){
    	if( -1 == config.services.currency.currencies.from.indexOf(src.toUpperCase()) )
    	 throw new SourceCurrencyNotSupportedError(src.toUpperCase());
    	if( -1 == config.services.currency.currencies.to.indexOf(end.toUpperCase()) )
       	 throw new EndCurrencyNotSupportedError(end.toUpperCase());
    }

    return {
        getRate: getRate
    };

}();

module.exports = MockCurrencyService;
