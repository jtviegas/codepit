"use strict";
process.env.MODE = 'TEST';

var expect = require('chai').expect;
var assert = require('chai').assert;

var factory = require('../src/services/currency/factory');
const SourceCurrencyNotSupportedError = require('../src/representations/errors').SourceCurrencyNotSupportedError;
const EndCurrencyNotSupportedError = require('../src/representations/errors').EndCurrencyNotSupportedError;


describe('running currency service unit tests:', function() {

	var service = null;
	
	before(function(done) {
		service = factory.get();
		done();
	});

    describe('service operations' , function() {

        it('1* should get a different value for USD->GBP there', function(done) {
        	let dol = 10.0;
            service.getRate('USD','GBP',function(err,o){
                expect(err).to.be.null;
                let newval = o*dol
                expect(newval).not.to.equal(dol);
                done();
            });
            
         });
        
        it('2* should get a different value for USD->EUR there', function(done) {
        	let dol = 10.0;
            service.getRate('USD','EUR',function(err,o){
                expect(err).to.be.null;
                let newval = o*dol
                expect(newval).not.to.equal(dol);
                done();
            });
            
         });
        
        it('3* should get an error for unsupported source currency', function(done) {
            service.getRate('GBP','EUR',function(err,o){
                expect(err).not.to.be.null;
                expect( err instanceof SourceCurrencyNotSupportedError).to.be.true;
                done();
            });
            
         });
        
        it('4* should get an error for unsupported end currency', function(done) {
            service.getRate('USD','KWA',function(err,o){
                expect(err).not.to.be.null;
                expect( err instanceof EndCurrencyNotSupportedError ).to.be.true;
                done();
            });
            
         });
        
        it('4* should get the same value for USD->USD there', function(done) {
        	let dol = 10.0;
            service.getRate('USD','USD',function(err,o){
                expect(err).to.be.null;
                let newval = 1.00*dol
                expect(newval).to.equal(dol);
                done();
            });
            
         });

    });
});

