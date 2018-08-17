"use strict";

var logger = require('./common').logger;
var config = require('../config');

var CurrencyService = function(){


    var getRate = function(srcCurrency, endCurrency, callback){
        logger.debug('[CurrencyService.getRate] IN');
        callback(null, 1.07);
        logger.debug('[CurrencyService.getRate] OUT');
    };

    return {
        getRate: getRate
    };

}();

module.exports = CurrencyService;
