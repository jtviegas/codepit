/*
 * spa.shell.js
 * Shell module for SPA
 */

/*jslint         browser : true, continue : true,
 devel  : true, indent  : 2,    maxerr   : 50,
 newcap : true, nomen   : true, plusplus : true,
 regexp : true, sloppy  : true, vars     : false,
 white  : true
 */
/*global $, spa */

ws.shell = (function() {
	// ---------------- BEGIN MODULE SCOPE VARIABLES --------------
	var configMap = {
		anchor_schema_map : {},
		main_html : String() + '<div class="header">'

		+ '</div>' + '<div class="body"></div>' + '<div class="footer"></div>'
	}, stateMap = {
		$container : null,
		anchor_map : {}
	}, jqueryMap = {},
	// functions
	copyAnchorMap, setJqueryMap, toggleModule, changeAnchorPart, onHashchange, initModule, loadModules;
	
	// Returns copy of stored anchor map; minimizes overhead
	copyAnchorMap = function() {
		return $.extend(true, {}, stateMap.anchor_map);
	};

	setJqueryMap = function() {
		var $container = stateMap.$container;

		jqueryMap = {
			$container : $container,
			$header : $container.find('.header'),
			$modules : new Array()
		};
	};

	toggleModule = function(mod, statusProposed) {
		var fn;
		if ( mod.body.is(':visible') ){
			if(statusProposed == 'open') return true;
			fn= function(obj){
				obj.header.css("border-radius", "5px 5px 5px 5px");
				obj.body.css("z-index", "-1");
				
			};
		}
		else{
			if(statusProposed == 'closed') return true;
			mod.body.css("z-index", "100");
			fn= function(obj){
				obj.header.css("border-radius", "5px 5px 0px 0px");
			};
			
		}

		mod.body.slideToggle(600,fn(mod));
		return true;
	};
	
	changeAnchorPart = function(arg_map) {
		var anchor_map_revise = copyAnchorMap(), bool_return = true, key_name = null, key_name_dep;

		// Begin merge changes into anchor map
		KEYVAL: for (key_name in arg_map) {
			if (arg_map.hasOwnProperty(key_name)) {

				// skip dependent keys during iteration
				if (key_name.indexOf('_') === 0) {
					continue KEYVAL;
				}

				// update independent key value
				anchor_map_revise[key_name] = arg_map[key_name];

				// update matching dependent key
				key_name_dep = '_' + key_name;
				if (arg_map[key_name_dep]) {
					anchor_map_revise[key_name_dep] = arg_map[key_name_dep];
				} else {
					delete anchor_map_revise[key_name_dep];
					delete anchor_map_revise['_s' + key_name_dep];
				}
			}
		}
		// End merge changes into anchor map

		// Begin attempt to update URI; revert if not successful
		try {
			$.uriAnchor.setAnchor(anchor_map_revise);
		} catch (error) {
			// replace URI with existing state
			$.uriAnchor.setAnchor(stateMap.anchor_map, null, true);
			bool_return = false;
		}
		// End attempt to update URI...

		return bool_return;
	};

	onHashchange = function(event) {
		//get current anchor map
		var anchor_map_previous = copyAnchorMap(), 
		anchor_map_proposed; 
		
		// attempt to parse anchor
		try {
			anchor_map_proposed = $.uriAnchor.makeAnchorMap();
		} catch (error) {
			$.uriAnchor.setAnchor(anchor_map_previous, null, true);
			return false;
		}
		stateMap.anchor_map = anchor_map_proposed;

		/* ---------- module one specific ---------- */
		var _s_modOne_previous, _s_modOne_proposed;
		
		// convenience vars
		_s_modOne_previous = anchor_map_previous._s_modOne;
		_s_modOne_proposed = anchor_map_proposed._s_modOne;

		// Begin adjust module if changed
		if (!anchor_map_previous || _s_modOne_previous !== _s_modOne_proposed) {
			toggleModule(jqueryMap.$modules[0], _s_modOne_proposed);
		}
		/* ----------------------------------------- */
		
		/* ---------- module quotes specific ---------- */
		var _s_modQuotes_previous, _s_modQuotes_proposed;
		
		// convenience vars
		_s_modQuotes_previous = anchor_map_previous._s_quotes;
		_s_modQuotes_proposed = anchor_map_proposed._s_quotes;

		// Begin adjust module if changed
		if (!anchor_map_previous || _s_modQuotes_previous !== _s_modQuotes_proposed) {
			toggleModule(jqueryMap.$modules[1], _s_modQuotes_proposed);
		}
		/* ----------------------------------------- */
		
		return false;
	};

	
	
	loadModules = function() {
		
		/* -------------------------------------------- */
		/* ---------- module one specific ---------- */
		/* -------------------------------------------- */
		var modOne_html = String() + ' <div class="mod" id="one"> '
				+ ' 	<div class="modHeader">' + ws.mod.name + '</div>'
				+ ' 	<div class="modBody"></div>' + ' </div>';
		// set up module header
		jqueryMap.$header.append(modOne_html);
		var modOne = jqueryMap.$header.find('.mod#one');
		var modOnePointers = {
				header : 	modOne.find('.modHeader'),
				body : 	modOne.find('.modBody')
			};
		jqueryMap.$modules[0] = modOnePointers;

		// configure and initialize feature modules
		ws.mod.configModule({});
		ws.mod.initModule(modOnePointers.body);
		
		modOnePointers.header.click(function(){
			changeAnchorPart({
				modOne : ($(this).next().is(':visible') ? 'closed' : 'open')
			});
			return false;
		});

		//update the config map
		configMap.anchor_schema_map.modOne= {
			open : true,
			closed : true
		};
	
		modOnePointers.body.css("display","none");
		/* -------------------------------------------- */
		
		/* -------------------------------------------- */
		/* ---------- module quotes specific ---------- */
		/* -------------------------------------------> */
		
		var modQuotes_html = String() + ' <div class="mod" id="quotes"> '
				+ ' 	<div class="modHeader">' + ws.quotes.name + '</div>'
				+ ' 	<div class="modBody"></div>' + ' </div>';
		// set up module header
		jqueryMap.$header.append(modQuotes_html);
		var modQuotes = jqueryMap.$header.find('.mod#quotes');
		var modQuotesPointers = {
				header : 	modQuotes.find('.modHeader'),
				body : 	modQuotes.find('.modBody')
			};
		jqueryMap.$modules[1] = modQuotesPointers;

		ws.repository.configModule();
		ws.repository.initModule();
		
		ws.model.configModule({ repository : ws.repository});
		ws.model.initModule();
		
		// configure and initialize feature modules
		ws.quotes.configModule({ model: ws.model});
		ws.quotes.initModule(modQuotesPointers.body);
		
		modQuotesPointers.header.click(function(){
			changeAnchorPart({
				quotes : ($(this).next().is(':visible') ? 'closed' : 'open')
			});
			return false;
		});

		//update the config map
		configMap.anchor_schema_map.quotes= {
			open : true,
			closed : true
		};
	
		modQuotesPointers.body.css("display","none");
		/* <------------------------------------------- */
		
	};

	// -------------------- END EVENT HANDLERS --------------------

	// ------------------- BEGIN PUBLIC METHODS -------------------
	// Begin Public method /initModule/
	initModule = function($container) {
		// load HTML and map jQuery collections
		stateMap.$container = $container;
		$container.html(configMap.main_html);
		setJqueryMap();

		loadModules();
		
		// configure uriAnchor to use our schema
		$.uriAnchor.configModule({
			schema_map : configMap.anchor_schema_map
		});

		$(window).bind('hashchange', onHashchange).trigger('hashchange');

	};
	// End PUBLIC method /initModule/

	return {
		initModule : initModule
	};
	// ------------------- END PUBLIC METHODS ---------------------
}());
