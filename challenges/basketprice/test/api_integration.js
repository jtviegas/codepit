
"use strict";
process.env.MODE = 'NOTEST';

const request = require('supertest');
const assert = require('chai').assert;
const expect = require('chai').expect;
const syncRequest = require('sync-request');
const config = require('../src/config');
const common = require('../src/services/common');

var baseFolder = __dirname + '/../src';

describe('price endpoint tests', function() {
	
    var app;
    var quotes;
    var ENDPOINT = '/api/price';
    
    before(function(done) {
        app = require(baseFolder + '/index.js');
        let endpoint = config.services.currency.api + '&currencies=' + config.services.currency.currencies.to.join() + '&source=' + config.services.store.currency;
        let res = syncRequest('GET', endpoint);
        let body = JSON.parse(res.getBody('utf8'));
        quotes = body.quotes;
        done();
    });


    describe('testing app' , function() {
    	
    	it('1* should require basic authentication to post object', function(done) {
        	
        	var obj = {};
            request(app).post(ENDPOINT)
                .set('Content-Type', 'application/json')
                .set('Accept', 'application/json')
                .send(obj)
                .end(function(err, res) {
                    expect(res.status).to.be.equal(401)
                    done();
                });
        });

        it('2* should price a simple basket in EUR', function(done) {
        	
        	let basket = { items: ["Milk","Milk","Milk","Apples"] , currency: "EUR" };
            request(app).post(ENDPOINT)
                .set('Content-Type', 'application/json')
                .set('Accept', 'application/json')
                .set('Authorization', 'Basic ' + new Buffer('user:passw0rd').toString('base64'))
                .send(basket)
                .end(function(err, res) {
                	
                    expect(err).to.be.null;
                    expect(res.status).to.be.equal(200);

                    assert.equal( common.roundToTwoDecimals( 2.62 * quotes.USDEUR ), res.body.total);
                    done();
                });
        });
        
        it('3* should price a simple basket in GBP', function(done) {
        	
        	let basket = {
        			items: ["Milk","Milk","Milk","Apples"]
        			, currency: "GBP"
        	};
            request(app).post(ENDPOINT)
                .set('Content-Type', 'application/json')
                .set('Accept', 'application/json')
                .set('Authorization', 'Basic ' + new Buffer('user:passw0rd').toString('base64'))
                .send(basket)
                .end(function(err, res) {
                	
                    expect(err).to.be.null;
                    expect(res.status).to.be.equal(200);

                    assert.equal( common.roundToTwoDecimals( 2.62 * quotes.USDGBP ), res.body.total);
                    done();
                });
        });
    });
    



});