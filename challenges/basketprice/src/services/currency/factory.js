"use strict";

const fs = require('fs');

var logger = require('../common').logger;

var CurrencyServiceFactory = (function() {

	var modules = {};

	var init = function() {

		fs.readdirSync(__dirname).forEach(f => {
			logger.debug('[CurrencyServiceFactory.init] processing file %s', f);
			let moduletype = f;
			let moduleFolder = __dirname + '/' + moduletype;
			let s = fs.statSync(moduleFolder);
			if (s.isDirectory()) {
				logger.debug('[CurrencyServiceFactory.init] processing module %s', moduletype);
				if (fs.statSync(moduleFolder + '/' + 'index.js').isFile()) {
					logger.info('[CurrencyServiceFactory.init] loading module %s from ', moduletype, moduleFolder);
					let module = require(moduleFolder);
					console.log(module);
					modules[moduletype.toLowerCase()] = module;
					console.log('modules', modules);
					logger.info('[CurrencyServiceFactory.init] loaded %s module', moduletype);

				}
			}


		});

	};

	var get = function() {
		logger.debug('[CurrencyServiceFactory.get] IN');

		let moduletype = 'base';
		if (process.env.MODE && process.env.MODE == 'TEST')
			moduletype = 'mock';

		console.log("modules:", modules);
		if (!modules[moduletype])
			throw new Error("!!! couldn't find module " + moduletype + " !!!");

		logger.debug('[CurrencyServiceFactory.get] OUT');
		return modules[moduletype];
	};

	init();

	return {
		get : get
	};

})();

module.exports = CurrencyServiceFactory;