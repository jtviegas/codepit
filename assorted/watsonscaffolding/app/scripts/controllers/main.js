'use strict';

/**
 * @ngdoc function
 * @name watsonscaffoldingApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the watsonscaffoldingApp
 */
angular.module('watsonscaffoldingApp')
  .controller('MainCtrl', function ($scope, answers, $alert) {
  	
    if(!$scope.question)
  		$scope.question = {};

    $scope.feedbackToggles = [];
    $scope.answers = [];
    $scope.validQuestion = true;
    $scope.ratingMax=5;
    $scope.ratingAverage=3;

  	var noQuestionAlert = $alert(
  		{	title: 'no data', 
  			content: 'Please provide a question!', 
  			container: '#alertContainer',
  			type: 'info', 
  			show: false
  		}
  	);

  	$scope.submit = function(){
      //clear state
      $scope.answers = [];
      $scope.feedbackToggles = [];
      //hide alert message
      noQuestionAlert.hide();
      
      if(!$scope.question.text){
        $scope.validQuestion = false;
        noQuestionAlert.show();
        return;
      }
      
      $scope.validQuestion = true;
      var promise = answers.get('');
      promise.then(function(o) {
        console.log('success');
        $scope.feedbackToggles = new Array(o.length);
        $scope.feedbackToggles.fill(false);
        $scope.answers = o;
      }, function(reason) {
         console.log('fail:' + reason);
      });
    }

    $scope.submitFeedback = function(){

    };


    

  });


