'use strict';

/**
 * @ngdoc function
 * @name watsonscaffoldingApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the watsonscaffoldingApp
 */
angular.module('watsonscaffoldingApp')
  .controller('MainCtrl', function ($scope, $alert) {
  	if(!$scope.question)
  		$scope.question = {};

  	var alert = $alert(
  		{	title: 'Holy guacamole!', 
  			content: 'Best check yo self, you\'re not looking too good.', 
  			placement: 'top', 
  			type: 'info', 
  			show: false
  		}
  	);

  	$scope.submit = function(){
  		console.log($scope.askWatson.$valid);
  		alert.show();
    }

  });
