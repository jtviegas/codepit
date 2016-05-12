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
			restUrl : null,
		},
		//local => '/restlayer-1.0-SNAPSHOT/quotes'
		//openshift => '/restlayer/quotes'
		restUrl : window.location.origin + '/restlayer-1.0-SNAPSHOT/quotes',
		
	}, 
	stateMap = {},

	configModule, initModule, 
	removeAll, getAll, persist, remove, get;

	
	removeAll = function(successFunc, errorFunc) {
		
		var settings = {
				url : configMap.restUrl + "/delete/all",
				type: "DELETE",
				dataType: "JSON",
				success: successFunc,
				error : errorFunc
		};
		$.ajax(settings);
		
	};
	
	getAll = function(successFunc, errorFunc) {

		var settings = {
				url : configMap.restUrl + "/all",
				type: "GET",
				dataType: "JSON",
				success: successFunc,
				error : errorFunc
		};
		$.ajax(settings);

	};

	persist = function(_quote, successFunc, errorFunc) {
		
		var settings = {
				url : configMap.restUrl + "/add",
				type: "POST",
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(_quote),
				success: successFunc,
				error:function(res){
						alert("Bad thing happened! " + res.statusText);
					}
		};
		$.ajax(settings);
	};

	remove = function(_id, successFunc, errorFunc) {
		var settings = {
				url : configMap.restUrl + "/delete/" + _id,
				type: "DELETE",
				dataType: "JSON",
				success: successFunc,
				error : errorFunc
		};
		$.ajax(settings);
	};

	get = function(_id, successFunc, errorFunc) {
		var settings = {
				url : configMap.restUrl + "/" + _id,
				type: "GET",
				dataType: "JSON",
				success: successFunc,
				error : errorFunc
		};
		$.ajax(settings);
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
		remove : remove,
		get : get
	};
}());
