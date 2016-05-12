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
		main_html : String()
				+ "<div class='modBodyCenter'>"
				+ "<div class='quoteTable'></div>"
				+ "</div>"
				+ "<div class='modBodyFooter'>"
				+ "		<form class='quotes_form'>"
				+ "			<div class='row'>"
				+ "				<input class='wgt-input-regular notShown' type='text' name='id' id='id'/>"
				+ "			</div>"
				+ "			<div class='row'>"
				+ "				<label for='author'>author</label>"
				+ "				<input class='wgt-input-regular' type='text' name='author' id='author' required> </input>"
				+ "				<span class='verifyMessage'>please review!</span>"
				+ "			</div>"
				+ "			<div class='row'>"
				+ "				<label for='quote'>quote</label>"
				+ "				<textarea class='wgt-input-regular' rows='4' name='quote' id='quote' required></textarea>"
				+ "				<span class='verifyMessage'>please review!</span>"
				+ "			</div>"
				+ "			<div class='row'>"
				+ "				<input class='wgt-input-small right' type='submit' id='submit'/>"
				+ "			</div>" 
				+ "		</form>"
				+ "</div>",
		settable_map : {
			model : null
		},
		model : null
	}, stateMap = {
		container : null,
		name : "quotes ui",
		quote : {}
	}, jqueryMap = {},

	/* public methods */
	setJqueryMap, configModule, initModule,
	/* private methods */
	insertQuoteInTable, loadQuotes,  
	setupEvents, setupSubmitQuoteEvent, setupDeleteQuoteEvent 
	;

	

	/*----------------------------------------------------*/
	/*--------------------PRIVATE METHODS-----------------*/
	/*----------------------------------------------------*/

	setJqueryMap = function() {
		jqueryMap = {
			container : stateMap.container,
			quoteForm : null
		};
	};
	
	insertQuoteInTable = function(quote) {
		var container = $('.quoteTable');
		var quoteFormat = "<div class='quoteEntry' id='{0}'>" + "<dt>{1}</dt>"
				+ "<dd>{2} (<div id='x'>x</div>)</dd>" + "</div>";
		container.append(quoteFormat.replace('\{0\}', quote.id).replace(
				'\{1\}', quote.author).replace('\{2\}', quote.quote));
	};

	setupDeleteQuoteEvent = function(){
		
		var table = $(".quoteTable");
		
		table.delegate("div #x","click",function(){
	          var idValue=$(this).parentsUntil('.quoteEntry').parent().attr('id');
	          configMap.model.removeQuote(idValue);
	        });
		
		$.gevent.subscribe(table, 
			'ws.model-quote.remove',
			function(){
				// first argument is the event object, we want the id
					var quoteId = arguments[1];
					table.children('.quoteEntry[id="' + quoteId + '"]').remove();
			});
		
	};
	
	setupSubmitQuoteEvent = function() {
		var submit = $('#submit');
		submit.click(function(event) {
			
			event.preventDefault();
			/*
			authorWgt = $('#author')[0];
			quoteWgt = $('#quote')[0];
			
			
			authorWgt = jqueryMap.quoteForm.author;
			quoteWgt = jqueryMap.quoteForm.quote;
			*/
			
				if( !Modernizr.input.required )
				{
					var $msg = $( "<div id='reqMessage'>Required Fields Missing</div>" );
					$msg.css( "background-color", "yellow" ).hide();
					$( ".modBodyFooter" ).append( $msg );
					$( "#submit" ).on("submit", function(e){
						$( "input[required]" ).each(function(){
								if ( $(this).val() === "" ) {
									$( "#reqMessage" ).show();
									$( this ).css( "border" , "1px solid red" );
									e.preventDefault();
								}
						});
					});
				}
			/*
			 * if(''==authorWgt.value || ''==quoteWgt.value) { alert('author and
			 * quote must have a value!') return; }
			 */
				
				/*
			if ('' == authorWgt) {
				authorWgt.focus(function(){
					$( this ).next( ".verifyMessage" ).css( "display", "inline" ).fadeOut( 1000 );
				});
				return;
			}
			
			if ('' == quoteWgt) {
				quoteWgt.focus(function(){
					$( this ).next( ".verifyMessage" ).css( "display", "inline" ).fadeOut( 1000 );
				});
				return;
			}
			
				
			var quote = {};

			quote.author = authorWgt.value;
			quote.quote = quoteWgt.value;
			quote.id = null;
			*/
			
			configMap.model.persistQuote(stateMap.quote);
	
		});
		
		
		$.gevent.subscribe(submit, 
			'ws.model-quote.persist',
			function(){
				var quote = arguments[1];
				insertQuoteInTable(quote);
				resetBinding();
				/*
				authorWgt.value = '';
				quoteWgt.value = '';
				*/
		});

	};

	resetBinding = function(){
		$(stateMap.quote).setField("id", null);
		$(stateMap.quote).setField("author", null);
		$(stateMap.quote).setField("quote", null);
	};
	
	loadQuotes = function(quotes) {
		for ( var i = 0; i < quotes.length; i++)
			insertQuoteInTable(quotes[i]);
	};
	
	setupEvents = function() {
		setupSubmitQuoteEvent();
		setupDeleteQuoteEvent();
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
		
		$("form").link(stateMap.quote);
		
		setupEvents();
		loadQuotes(configMap.model.getQuotes());
		return true;
	};
	
	return {
		configModule : configModule,
		initModule : initModule,
		name : stateMap.name
	};
	
}());
