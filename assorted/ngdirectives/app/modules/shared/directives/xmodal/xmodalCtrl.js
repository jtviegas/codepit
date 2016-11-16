(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:xmodalCtrl
	* @description
	* # xmodalCtrl
	* Controller of the app
	*/

	angular
		.module('ngdirectives')
		.controller('XmodalCtrl', Xmodal );

		Xmodal.$inject = [];

		/*
		* recommend
		* Using function declarations
		* and bindable members up top.
		*/

		function Xmodal() {
			/*jshint validthis: true */
			var vm = this;

		}

})();
