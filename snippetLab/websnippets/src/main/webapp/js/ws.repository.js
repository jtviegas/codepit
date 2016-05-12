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

ws.repository = (function() {
	'use strict';
	var configMap = {
		settable_map : {
	
		}
	}, 
	stateMap = {

	},

	configModule, initModule, 
	removeAll, getAll, persist, remove;

	
	removeAll = function() {
		
	};
	
	getAll = function() {
		 var data = new Array;
         data.push({
             id: 'a1',
             author: 'john',
             quote: 'blabla'
         });
         data.push({
             id: 'a2',
             author: 'james',
             quote: 'blabla'
         });
         return data;
	};

	persist = function(_quote) {
	};

	remove = function(_id) {

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
		removeAll : removeAll,
		getAll : getAll,
		persist : persist,
		remove : remove
	};
}());
