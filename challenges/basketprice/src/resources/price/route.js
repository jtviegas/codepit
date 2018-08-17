"use strict";

var express = require('express');
var bodyParser = require('body-parser');
var logger = require('../../services/common/apputils').logger;
var auth = require('../auth');

var functions = require('./functions');
logger.debug('started loading...');
var router = express.Router();
router.use(bodyParser.json({limit: '1mb'})); // for parsing application/json
router.post('/', auth.passport.authenticate('basic', { session: false }), functions.price);
logger.debug('...finished loading.');
module.exports = router;
