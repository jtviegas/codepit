/*
 * spa.chat.js
 * Chat feature module for SPA
 */

/*jslint         browser : true, continue : true,
 devel  : true, indent  : 2,    maxerr   : 50,
 newcap : true, nomen   : true, plusplus : true,
 regexp : true, sloppy  : true, vars     : false,
 white  : true
 */

/*global $, ws */

ws.quotes = (function() {

	/*----------------------------------------------------*/
	/*--------------------VARIABLES-----------------------*/
	/*----------------------------------------------------*/
	
	var configMap = {
		main_html : String() +
				"<script id='quoteListTemplate' type='text/x-jsrender'>" +
					"{^{for quotes}}" +
						"<div class='quoteEntry'>" +
							"<input type='hidden' data-link='id'/>" +
							"<dt>{{:author}}</dt>" +
							"<dd>{{:text}} (<div id='x' class='remove'>x</div>)</dd>" +
						"</div>" +
					"{{/for}}" +	
				"</script>" +
				"<script id='quoteFormTemplate' type='text/x-jsrender'>" +
					"<div class='row'>" +
						"<input class='wgt-input-regular notShown' type='text' data-link='id'/>" +
					"</div>" +
					"<div class='row'>" +
						"<label for='author'>author</label>" +
						"<input class='wgt-input-regular' id='author' type='text' data-link='author' placeholder='author name here' required/>" +
						"<span class='verifyMessage'>please review!</span>" +
					"</div>" +
					"<div class='row'>" +
						"<label for='text'>quote</label>" +
						"<textarea class='wgt-input-regular' rows='4' id='text' data-link='text' placeholder='quote here' required></textarea>" +
						"<span class='verifyMessage'>please review!</span>" +
					"</div>" +
				"</script>" +
				"<div class='modBodyCenter'>" +
					"<div class='quoteTable' id='quoteListPlaceholder'>" +
					"</div>" +
				"</div>" +
				"<div class='modBodyFooter'>" +
					"<form class='quoteForm' id='quoteForm'>" +
						"<div id='quoteFormPlaceholder'>" +
						"</div>" +
						"<div class='row'>" +
							"<input class='wgt-input-small right' type='submit' id='submit'/>" +
						"</div>" +
					"</form>" + 
				"</div>",
		settable_map : {
			model : null
		},
		model : null
	}, stateMap = {
		container : null,
		name : "quotes ui",
		quote : { id: 0 , author: null, text: null},
		quotes : null
	}, jqueryMap = {},

	/* public methods */
	setJqueryMap, configModule, initModule,
	/* private methods */
	insertQuoteInTable, loadQuotes, setupEvents, setupSubmitQuoteEvent, setupDeleteQuoteEvent;

	/*----------------------------------------------------*/
	/*--------------------PRIVATE METHODS-----------------*/
	/*----------------------------------------------------*/

	setJqueryMap = function() {
		jqueryMap = {
			container : stateMap.container,
			quoteForm : null,
			quoteListTemplate: null,
			quoteListPlaceholder: null,
			quoteFormTemplate: null,
			quoteFormPlaceholder: null,
			quoteTable: null
		};
		
		jqueryMap.quoteForm = $('.modBodyFooter').find('#quoteForm');
		jqueryMap.quoteListTemplate = $.templates("#quoteListTemplate");
		jqueryMap.quoteListPlaceholder = $("#quoteListPlaceholder");
		jqueryMap.quoteFormTemplate = $.templates("#quoteFormTemplate");
		jqueryMap.quoteFormPlaceholder = $("#quoteFormPlaceholder");
		jqueryMap.quoteTable = $('.quoteTable');
	};


	/*--------------------EVENT RELATED METHODS-----------*/
	//$.gevent.publish('ws.model-quote.removeAll');
	
	
	 setupDeleteQuoteEvent = function() {

		jqueryMap.quoteTable.on("click", ".remove", function(){
	    	var index = $.view(this).index;
	    	var id = stateMap.quotes[index].id;
	    	configMap.model.removeQuote(id, 
					new function(){
						$.gevent.publish('ws.model-quote.remove', index );
					}
				);
	    	
	    });

	    $.gevent.subscribe(jqueryMap.quoteTable, 'ws.model-quote.remove', function() {
			// first argument is the event object, we want the id
			var index = arguments[1];
			$.observable(stateMap.quotes).remove(index);
						
		});

	};
	
	

	setupSubmitQuoteEvent = function() {
		
		jqueryMap.quoteFormTemplate.link("#quoteFormPlaceholder", stateMap.quote);
		var submit = $('#submit');
		submit.click(function(event) {
					event.preventDefault();

					if(jqueryMap.quoteForm[0].checkValidity()){
						//if id is null then we are adding a quote, we can't send an id
						if(null == stateMap.quote.id || 0 == stateMap.quote.id)
							delete stateMap.quote.id;
						
						configMap.model.persistQuote(stateMap.quote,
								function(data) {
									$.gevent.publish('ws.model-quote.persist',
											[ data ]);
								});
					}
				});

		$.gevent.subscribe(submit, 'ws.model-quote.persist', function() {
			var quote = arguments[1];
			$.observable(stateMap.quotes).insert(quote);
			$.observable(stateMap.quote).setProperty('id', null);
			$.observable(stateMap.quote).setProperty('author', null);
			$.observable(stateMap.quote).setProperty('text', null);
			
		});

	};
	
	setupGetAllQuotesEvent = function() {

		$.gevent.subscribe(jqueryMap.quoteTable, 'ws.model-quote.getAll', function(event,
				dataArr) {
			stateMap.quotes = dataArr;
			jqueryMap.quoteListTemplate.link("#quoteListPlaceholder", {quotes: stateMap.quotes});
		});

	};
	
	setupEvents = function() {
		setupSubmitQuoteEvent();
		setupDeleteQuoteEvent();
		setupGetAllQuotesEvent();
	};
	
	
	setupData = function() {

		configMap.model.getQuotes(function(dataArr) {
			var wrapper = new Array;
			wrapper.push(dataArr);
			$.gevent.publish('ws.model-quote.getAll', wrapper);
		});
		
	};

	/*----------------------------------------------------*/
	/*--------------------PUBLIC METHODS------------------*/
	/*----------------------------------------------------*/
	configModule = function(input_map) {

		configMap = ws.util.setConfigMap({
			input_map : input_map,
			settable_map : configMap.settable_map,
			config_map : configMap
		});

		return true;
	};

	initModule = function(container) {
		
		container.append(configMap.main_html);
		stateMap.container = container;
		setJqueryMap();
		setupEvents();
		setupData();
		
		

		return true;
	};

	return {
		configModule : configModule,
		initModule : initModule,
		name : stateMap.name
	};

}());
