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
		.module('ngdirectives')
		.controller('HomeCtrl', Home);

	Home.$inject = ['$scope'];

	/*
	* recommend
	* Using function declarations
	* and bindable members up top.
	*/

	function Home($scope) {

		// Set show modal to false/ hide from HTML
		$scope.showModal = false;

		// Toggle function to show and hide modal from HTML
		$scope.toggleModal = function(){
			$scope.showModal = !$scope.showModal;
		};

	}

})();
