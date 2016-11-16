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
		.controller('AppCtrl', App);

	App.$inject = ['$scope'];

	/*
	* recommend
	* Using function declarations
	* and bindable members up top.
	*/

	function App($scope) {

		$scope.menu = {
			entries : [
				{
					url: 'ccc'
	/*				, image:'bbb'*/
					, shortText:'aaa'
				}
			]
		};
	}

})();