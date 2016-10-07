var express = require('express');
var util = require('util');
var session = require('express-session');
var passport = require('passport');
var cookieParser = require('cookie-parser');
var cfenv = require('cfenv');

var config = require( __dirname + '/config');

//CONSTANTS
var PORT=8080;



var app = express();
app.set('port', process.env.PORT || PORT);

var sessionOptions = {
    name: 'sid'
    , saveUninitialized: true
    , secret: 'sllkcx;jslcjdsl;ka;ldkslksjalkjoiewrjg'
    , resave: true
};
app.use(cookieParser());
app.use(session(sessionOptions));
app.use(passport.initialize());
app.use(passport.session());

passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(obj, done) {
    done(null, obj);
});

var OpenIDConnectStrategy = require('passport-idaas-openidconnect').IDaaSOIDCStrategy;
var Strategy = new OpenIDConnectStrategy({
        authorizationURL : config.authorization_url,
        tokenURL : config.token_url,
        clientID : config.client_id,
        scope: 'openid',
        response_type: 'code',
        clientSecret : config.client_secret,
        callbackURL : config.callback_url,
        skipUserProfile: true,
        issuer: config.issuer_id},
    function(iss, sub, profile, accessToken, refreshToken, params, done)  {
        process.nextTick(function() {
            profile.accessToken = accessToken;
            profile.refreshToken = refreshToken;
            done(null, profile);
        })
    });

passport.use(Strategy);

app.get('/auth/authenticate', passport.authenticate('openidconnect', {}));

function ensureAuthenticated(req, res, next) {
    console.info( 'ensureAuthenticated session:' , req.session);
    if (!req.isAuthenticated()) {
        console.info( '[ensureAuthenticated] user is authenticated');
        req.session.originalUrl = req.originalUrl;
        res.redirect('/auth/authenticate');
    } else {
        console.info( '[ensureAuthenticated] user is NOT authenticated');
        return next();
    }
    console.info( 'OUT ensureAuthenticated session:' , req.session);
}

// handle callback, if authentication succeeds redirect to
// original requested url, otherwise go to /failure
app.get('/auth/callback',function(req, res, next) {
    console.info( 'IN /auth/callback session:' , req.session);
    var redirect_url = req.session.originalUrl;
    passport.authenticate('openidconnect', {
        successRedirect: redirect_url,
        failureRedirect: '/failure',
    })(req,res,next);
    console.info( 'OUT /auth/callback session:' , req.session);
});

app.get('/failure', function(req, res) {
    console.info( 'IN /failure session:' , req.session)
    res.set('Content-Type', 'text/html');
    res.status(200).send(new Buffer('<html><body><div>login failure</div><hr><a href=\'/\'>home</a></body></html>'));
    console.info( 'OUT /failure session:' , req.session);
});

app.get('/logout', function(req,res) {
    console.info( 'IN /logout session:' , req.session)
    req.session.destroy();
    req.logout();
    res.set('Content-Type', 'text/html');
    res.status(200).send(new Buffer('<html><body><div>you have been logged out</div><hr><a href=\'/\'>home</a></body></html>'));
    console.info( 'OUT /logout session:' , req.session);
});


app.get('/hello', ensureAuthenticated, function(req, res) {
    console.info( 'IN ensureAuthenticated session:' , req.session)
    var claims = req.user['_json'];
    console.log(claims);
    var html ="<p>Hello " + claims.given_name + " " + claims.family_name + ": </p>";

    html += "<pre>" + JSON.stringify(req.user, null, 4) + "</pre>";
    html += "<hr> <a href=\"/\">home</a>";
    //res.send('Hello '+ claims.given_name + ' ' + claims.family_name + ', your email is ' + claims.email + '<br /> <a href=\'/\'>home</a>');
    res.send(html);
    console.info( 'OUT ensureAuthenticated session:' , req.session);
});

app.get('/', function(req,res) {
    console.info( 'IN / session:' , req.session)
    res.set('Content-Type', 'text/html');
    res.status(200).send(new Buffer(util.format(
        '<html> <body> <h3>Ola!</h3> <div>you are authenticated: %s</div> <div><a href=\'/hello\'> secured hello </a> </div>' +
        ' <div> <a href=\'/logout\'> logout </a>  </div> </body> </html>', req.isAuthenticated() ? 'true' : 'false')));
    console.info( 'OUT / session:' , req.session);
});



// custom 404 page
app.use(function(req, res){
	//console.info(util.format('reached 404: %s', util.inspect(req)));
	res.type('text/plain');
	res.status(404);
	res.send('404 - Not Found');
});

// custom 500 page
app.use(function(err, req, res){
	console.info(util.format('reached 500: %s', err.stack));
	res.type('text/plain');
	res.status(500);
	res.send('500 - Server Error');
});

var server = app.listen(app.get('port'), function () {
  var host = server.address().address;
  var port = server.address().port;
  console.info(util.format('listening at http://%s:%s', host, port));

});

