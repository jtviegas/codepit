(function () {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:HomeCtrl
	* @description
	* # HomeCtrl
	* Controller of the app
	*/

	angular
		.module('angdirectives')
		.controller('HomeCtrl', Home);

	Home.$inject = ['$scope', 'homeService'];

	/*
	* recommend
	* Using function declarations
	* and bindable members up top.
	*/

	function Home($scope, homeService) {
		/*jshint validthis: true */
		var vm = this;
		vm.title = "Hello, angdirectives!";
		vm.version = "1.0.0";
		vm.listFeatures = homeService.getFeaturesList();
		$scope.modalShown = false;
		$scope.toggleModal = function(){
			$scope.modalShown = true;
		}

	}

})();
