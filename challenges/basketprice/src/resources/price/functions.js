"use strict";

var util = require('util');
var logger = require('../../services/common/apputils').logger;
var config = require('../../config');
var data = require('../../services/data/dataService');


var PriceFunctions = function(){

    var price = function(req,res){
        logger.debug('[PriceFunctions.price] IN');

        var obj = req.body;
        if(obj.id)
            res.status(422).send('trying to post object with an id');

        var callback = function(err, o){
            if(err){
                logger.error(err);
                res.status(500).end();
            }
            else {
                res.status(201).json(o);
                res.end();
            }
        };
        data.setPart(obj, callback);
        logger.debug('[PriceFunctions.price] OUT');
    };



    return {
        price: price
    };

}();

module.exports = PriceFunctions;
