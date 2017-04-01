'use strict';

var SwaggerExpress = require('swagger-express-mw');
var app = require('express')();
var auth = require(__dirname + '/api/controllers/auth');
module.exports = app; // for testing

var config = {
    appRoot: __dirname // required config
};

SwaggerExpress.create(config, function(err, swaggerExpress) {
    if (err) { throw err; }


    app.get('/:id', auth.validate)
    // install middleware
    swaggerExpress.register(app);

    var port = process.env.PORT || 10010;
    app.listen(port);

    if (swaggerExpress.runner.swagger.paths['/']) {
        console.log('try this:\ncurl http://127.0.0.1:' + port + '/?id=Scott');
    }
});
