(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:simplemodalDirective
	* @description
	* # simplemodalDirective
	* Directive of the app
	*/

	angular
		.module('angdirectives')
		.controller('SimpleModalCtrl', SimpleModalCtrl )
		.directive('simpleModal', simpleModalDir);
		function simpleModalDir () {

			var directive = {
				link: link,

				restrict: 'E',
				controller: 'SimpleModalCtrl',
				scope: {
				      show: '='
				    },
				replace: true, // Replace with template
				transclude: true, // To use custom content
				templateUrl:'app/modules/shared/directives/simplemodal/simplemodal.html',
			}

			return directive;

			function link(scope, element, attrs) {
				scope.windowStyle = {};

		      if (attrs.width) {
		        scope.windowStyle.width = attrs.width;
		      }
		      if (attrs.height) {
		        scope.windowStyle.height = attrs.height;
		      }

		      scope.hideModal = function() {
		        scope.show = false;
		      };
			}

		};

		SimpleModalCtrl.$inject = [];

		/*
		* recommend
		* Using function declarations
		* and bindable members up top.
		*/

		function SimpleModalCtrl() {
			/*jshint validthis: true */
			var vm = this;

		};


})();


		