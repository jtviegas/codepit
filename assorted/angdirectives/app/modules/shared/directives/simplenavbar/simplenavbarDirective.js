(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:simplenavbarDirective
	* @description
	* # simplenavbarDirective
	* Directive of the app
	*/

	angular
		.module('angdirectives')
		.directive('simpleNavbar', simpleNavbarDir)
		.controller('SimpleNavbarCtrl', SimpleNavbarCtrl );

		SimpleNavbarCtrl.$inject = ['$scope', '$location'];

		/*
		* recommend
		* Using function declarations
		* and bindable members up top.
		*/

		function SimpleNavbarCtrl($scope, $location) {

			 $scope.isActive = function(path){
	          var currentPath = $location.path().split('/')[1];
	          if (currentPath.indexOf('?') !== -1) {
	            currentPath = currentPath.split('?')[0];
	          }
	          return currentPath === path.split('/')[1];
	        };

		};


		function simpleNavbarDir () {

			var directive = {
				restrict: 'E',
				controller: 'SimpleNavbarCtrl',
				templateUrl:'app/modules/shared/directives/simplenavbar/simplenavbar.html',
			};

			return directive;
		};

})();
