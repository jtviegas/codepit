/*
 * spa.model.js
 * Model module
 */

/*jslint         browser : true, continue : true,
 devel  : true, indent  : 2,    maxerr   : 50,
 newcap : true, nomen   : true, plusplus : true,
 regexp : true, sloppy  : true, vars     : false,
 white  : true
 */
/*global TAFFY, $, ws */

ws.model = (function() {
	'use strict';
	var configMap = {
		settable_map : {
			repository : null
		},
		repository : null
	}, 
	stateMap = {

	},

	configModule, initModule, 
	clearQuotes, persistQuote, removeQuote, getQuotes;

	clearQuotes = function(successFunc, errorFunc) {
		configMap.repository.removeAll(successFunc, errorFunc);
	};
	
	getQuotes = function(successFunc, errorFunc) {
		configMap.repository.getAll(successFunc, errorFunc);
	};

	persistQuote = function(quote, successFunc, errorFunc) {
		configMap.repository.persist(quote, successFunc, errorFunc);
	};

	removeQuote = function(quoteId, successFunc, errorFunc) {

		// cannot remove unsaved quote
		if (!quoteId) {
			return false;
		}

		configMap.repository.remove(quoteId, successFunc, errorFunc);

	};

	configModule = function(input_map) {
		configMap = ws.util.setConfigMap({
			input_map : input_map,
			settable_map : configMap.settable_map,
			config_map : configMap
		});
		return true;
	};

	initModule = function() {

	};

	return {
		initModule : initModule,
		configModule : configModule,
		clearQuotes : clearQuotes,
		persistQuote : persistQuote,
		removeQuote : removeQuote,
		getQuotes : getQuotes
	};
}());
