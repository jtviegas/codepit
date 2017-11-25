/**
 * Created by joaovieg on 16/11/16.
 */
"use strict";

var request = require('supertest');
var assert = require('chai').assert;

describe('endpoints tests', function() {

    var app = require(__dirname + '/index');
    var sessionless = request(app);

    it('should return echo', function(done) {
        var msg = { 'text' : 'ola' };
        sessionless
            .post('/' )
            .set('Content-Type', 'application/json')
            .set('Accept', 'application/json')
            .send(msg)
            .expect(200)
            .end(function(err, res) {
                if (err)
                    return done(err);

                var o = res.body;
                assert.deepEqual(msg, o);
                done();
            });
    });

});