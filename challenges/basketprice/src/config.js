"use strict";

var fs = require('fs');

var Config = function() {

    var app = {
        name: 'basketprice'
    };
    
    var services = {
    	currency: {
    		api: 'http://apilayer.net/api/live?access_key=6e24d718d139a3041d027b6506dca7b8&format=1&currencies=EUR,GBP,CAD,PLN&source=USD'
    		, currencies: {
    			from: [ 'USD']
    			, to:[ 'EUR', 'GBP', 'USD' ]		 
    		}
    	}
    	, store: {
    		items: [
    			{name: "Soup", price: 0.65 }
    			, {name: "Bread", price: 0.80}
    			, {name: "Milk", price: 1.15, discount: { name: "Milk 50% off when 3", discount: 0.50, condition: {type: "quantity", value: 3} } }
    			, {name: "Apples", price: 1.00, discount: { name: "Apples 10% off", discount: 0.10, condition: {type: "quantity", value: 1} } }
    		]
    		, currency: 'USD'
    	}
    };

    var log = {
        dir: process.env.LOG_DIR || './logs'
        , level: process.env.LOG_LEVEL || 'info'
        , filename: process.env.LOG_FILENAME || 'trace.log'
        , filesize: process.env.LOG_FILESIZE || 1048576
        , filenum: process.env.LOG_FILENUM || 5
    }

    return {
        app: app
        , services: services
        , log: log
    };

}();

module.exports = Config;
