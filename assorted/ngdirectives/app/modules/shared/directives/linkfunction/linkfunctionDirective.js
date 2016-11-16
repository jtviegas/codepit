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
		.module('ngdirectives')
		.directive('linkFunction', linkFunction);

		function linkFunction () {

			var directive = {
				link: link,
				restrict: 'EA',
				controller: '',
				
				template: ''
				
			}

			return directive;

			function link(scope, element, attrs) {
				// write your code here
			}

		}

})();
