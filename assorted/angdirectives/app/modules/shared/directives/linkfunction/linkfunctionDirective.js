(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:linkfunctionDirective
	* @description
	* # linkfunctionDirective
	* Directive of the app
	*/

	angular
		.module('angdirectives')
		.directive('linkFunction', linkFunction);
		function linkFunction () {

			var directive = {
				restrict: 'EA',
				replace: true,
    			template: '<p style="background-color:{{colorName}}">Link Function Directive</p>',
				link: link,
				scope: true
			}

			return directive;

			function link(scope, element, attrs) {
				element.bind('click',function(){
		        	element.css('background-color','white');
			        scope.$apply(function(){ scope.color="white"; });
		      	});
		      	element.bind('mouseover',function(){
		        	element.css('cursor','pointer');
		      	});
			}

		}

})();
