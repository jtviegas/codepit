"use strict";

var util = require('util');
var common = require('../../services/common');
var logger = common.logger;
var config = require('../../config')
const storeServicefactory = require('../../services/store/factory');
const currencyServicefactory = require('../../services/currency/factory');
const BadBasketError = require('../../representations/errors').BadBasketError;
const EndCurrencyNotSupportedError = require('../../representations/errors').EndCurrencyNotSupportedError;
const SourceCurrencyNotSupportedError = require('../../representations/errors').SourceCurrencyNotSupportedError;

var PriceFunctions = function(){
	
	var store = storeServicefactory.get();
	var exchange = currencyServicefactory.get();
	
    var price = function(req,res){
        logger.debug('[PriceFunctions.price] IN', req);

        let basket = req.body;
        try{
			let price = store.getPrice(basket);
			price.currency = basket.currency;
			
			var callback = function(price, res){
				var f = function(e, o){
		            if(e){
		            	logger.error(e);
		            	if (e instanceof EndCurrencyNotSupportedError) 
		            		res.status(400).send(e.message);
		            	else if (e instanceof SourceCurrencyNotSupportedError) 
		            		res.status(400).send(e.message);
		            	else 
		            		res.status(500).send(e.message);
		            }
		            else {
		            	price.subtotal = common.roundToTwoDecimals(price.subtotal * o);
		            	price.discountAmt = common.roundToTwoDecimals(price.discountAmt * o);
		            	price.total = common.roundToTwoDecimals(price.total * o);
		            	res.status(200).json(price);
		            }
		        };
		        return {f: f};
			}(price, res);
			exchange.getRate(config.services.store.currency, basket.currency, callback.f);
		}
        catch (e) {
        	logger.error(e);
            if (e instanceof BadBasketError) 
            	res.status(422).send(e.message);
            else 
            	res.status(500).send(e.message);
        }
        finally{
        	logger.debug('[PriceFunctions.price] OUT');	
        }
        
    };



    return {
        price: price
    };

}();

module.exports = PriceFunctions;
