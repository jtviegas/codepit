(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:xnavbarDirective
	* @description
	* # xnavbarDirective
	* Directive of the app
	*/

	angular
		.module('ngdirectives')
		.directive('xnavbar', xnavbar);

		function xnavbar () {

			var directive = {
/*				link: link,*/
				restrict: 'E',
				replace: true,
				controller: 'XnavbarCtrl',
				scope: '=menu',
				templateUrl:'app/modules/shared/directives/xnavbar/xnavbar.html',
			}

			return directive;

/*			function link(scope, element, attrs) {
				for(var i = 0; i < scope.length; i++){
					element
				}
			}*/

		}

})();
