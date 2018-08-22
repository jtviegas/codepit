"use strict";
process.env.MODE = 'TEST';

var expect = require('chai').expect;
var assert = require('chai').assert;

var factory = require('../src/services/store/factory');
const BadBasketError = require('../src/representations/errors').BadBasketError;


describe('running store service unit tests:', function() {

	var service = null;

	before(function(done) {
		service = factory.get();
		done();
	});

	describe('store operations', function() {

		it('1* should get an error for wrong basket with no currency', function(done) {
			let basket = {
				items : [ "Milk", "Milk", "Milk", "Apples" ]
			};

			try {
				service.getPrice(basket);
				assert(false, 'service must throw BadBasketError')
			} catch (e) {
				expect(e instanceof BadBasketError).to.be.true;
				done();
			}

		});

		it('2* should get an error for wrong basket with no items', function(done) {
			let basket = {
				currency : "USD"
			};

			try {
				service.getPrice(basket);
				assert(false, 'service must throw BadBasketError')
			} catch (e) {
				expect(e instanceof BadBasketError).to.be.true;
				done();
			}

		});

		it('3* should get an error for really wrong basket with nothing', function(done) {
			let basket = {};

			try {
				service.getPrice(basket);
				assert(false, 'service must throw BadBasketError')
			} catch (e) {
				expect(e instanceof BadBasketError).to.be.true;
				done();
			}
		});

		it('4* should get an error for really wrong basket with empty items', function(done) {
			let basket = {
				items : [],
				currency : "USD"
			};

			try {
				service.getPrice(basket);
				assert(false, 'service must throw BadBasketError')
			} catch (e) {
				expect(e instanceof BadBasketError).to.be.true;
				done();
			}
		});

		it('5* should get a discount on milk and apples', function(done) {
			let basket = {
				items : [ "Milk", "Milk", "Milk", "Apples" ],
				currency : "USD"
			};

			let price = service.getPrice(basket);
			console.log('price', price)
			assert.equal(2.62, price.total);
			done();
		});



	});
});