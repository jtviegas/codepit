var express = require('express');
var util = require('util');
var cookieSession = require('cookie-session');
var cookieParser = require('cookie-parser');
var https = require('https');
const fs = require('fs');
var cors = require('cors');

//var swaggerUi = require('swagger-ui-express'), swaggerDocument = require('./swagger.json');

//custom modules
var logger = require('./services/common/apputils').logger;
var price = require('./resources/price/route.js');

//CONSTANTS
process.env.PORT = process.env.PORT || 3000;
process.env.MODE = process.env.MODE || 'PROD';
process.env.STORE = process.env.STORE || 'REAL';
var frontendDir = __dirname + '/static';

logger.info('[index.js] starting in mode: %s [dataStore: %s][frontend dir: %s]', process.env.MODE, process.env.STORE, frontendDir);

var cookieSessionProps = {
  name: 'session',
  keys: ['basketprice'],
  cookie: {
	  // 30 days cookies
    maxAge : 30*24*60*60*1000
  }
};

var app = express();
app.use(cookieSession(cookieSessionProps));
app.use(cookieParser());

app.set('port', process.env.PORT);

if( 'PROD' === process.env.MODE ){
  app.use(function(req, res, next){
    if( 'https' === req.headers['x-forwarded-proto'] )
      next();
    else {
      let msg = "https != x-forwarded-proto";
      logger.error(msg);
      res.status(400);
      res.send(msg);
    }
  });
}

if( 'TEST' === process.env.MODE ){
  app.use(cors({origin: 'http://localhost:3000'}));
}


//app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));
app.use('/api/price', price);

// custom 404 page
app.use(function(req, res){
	logger.info(util.format('reached 404'));
	res.type('text/plain');
	res.status(404);
	res.send('404 - Not Found');
});

// custom 500 page
app.use(function(err, req, res, next){
	logger.error(util.format('reached 500: %s', err.stack));
	res.type('text/plain');
	res.status(500);
	res.send('500 - Server Error');
});

httpsOpts = {
  key: fs.readFileSync(__dirname + '/sslkey.pem')
  , cert: fs.readFileSync(__dirname + '/sslcert.crt')
}

https.createServer(httpsOpts, app).listen(app.get('port'), function() {
  logger.info(util.format('split4ever started on https://localhost:%s', app.get('port')));
});

module.exports = app;
