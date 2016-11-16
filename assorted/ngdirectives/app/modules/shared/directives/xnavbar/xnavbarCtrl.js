(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:xnavbarCtrl
	* @description
	* # xnavbarCtrl
	* Controller of the app
	*/

	angular
		.module('ngdirectives')
		.controller('XnavbarCtrl', Xnavbar );

		Xnavbar.$inject = [];

		/*
		* recommend
		* Using function declarations
		* and bindable members up top.
		*/

		function Xnavbar() {
			/*jshint validthis: true */
			var vm = this;

		}

})();
