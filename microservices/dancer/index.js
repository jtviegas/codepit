/**
 * Created by joaovieg on 16/11/16.
 */

"use strict";

var appEnv = require('cfenv').getAppEnv();
var PORT = 3000;
if(!appEnv.isLocal)
    PORT = appEnv.port;

var MAX=1000000;

var util = require('util');
var express = require('express');
var path = require('path')
var bodyParser = require('body-parser');


// create a new express server
var app = express();

// Enable reverse proxy support in Express. This causes the the "X-Forwarded-Proto" header field to be trusted so its
// value can be used to determine the protocol. See  http://expressjs.com/api#app-settings for more details.
// this way we can know which protocol was used on the call to  bluemix, do bear in mind that even if you get through with https
// in the actual server we are only supporting http,  so there is a forward on the way in...
// But now you ask why... check route '/itsm/api/cognitive/maintenance' down there
app.enable('trust proxy');
app.use(bodyParser.json({limit: '50mb'})); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

app.get('/',  function(req,res) {
    var value= Math.floor(Math.random() * (MAX+1));
    res.status(200).json( { "result": value } );
});

// custom 404 page
app.use(function(req, res){
    res.type('text/plain');
    res.status(404);
    res.send('404 - Not Found');
});

// custom 500 page
app.use(function(err, req, res, next){
    res.type('text/plain');
    res.status(500);
    res.send('500 - Server Error');
});

app.listen(PORT, '0.0.0.0', function() {
    console.log("server starting on port " + PORT);
});

module.exports = app;
