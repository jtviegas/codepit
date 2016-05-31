'use strict';

/**
 * @ngdoc function
 * @name watsonscaffoldingApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the watsonscaffoldingApp
 */
angular.module('watsonscaffoldingApp')
  .controller('MainCtrl', function ($scope, api, $alert, $window, APP_CONSTANTS) {
  	
    $scope.question = {};
    $scope.validQuestion = true;
    $scope.queryOutcome = {};
    $scope.feedbackToggles = [];
    $scope.ratingMax = APP_CONSTANTS.RATING_MAX;


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
      $scope.queryOutcome = {};
      $scope.validQuestion = true;
      noQuestionAlert.hide();
      
      if(!$scope.question.text){
        $scope.validQuestion = false;
        noQuestionAlert.show();
        return;
      }
      console.log('going to find matches for question: ' + $scope.question.text);
      var promise = api.getQuestionMatches($scope.question.text);
      promise.then(function(o) {
        console.log('success');
        $scope.feedbackToggles = new Array(o.length);
        $scope.feedbackToggles.fill(false);
        $scope.queryOutcome = o;
      }, function(reason) {
         console.log('fail:' + reason);
      });
    };

    $scope.followLink = function(index){
      $scope.queryOutcome.matches[index].click = true;
    };    

    $scope.submitFeedback = function(){

    };

  })
  .service( 'api', function (utils, $q, $resource){

    var counter = 0;
    var numberOfMocks  = 16;

      var getMockAnswers = function(query){

        var question = {};
        var index = 0;
        question.id = ++counter;
        question.text = query;

        var outcome = {};
        outcome.question = question;
        outcome.matches = [];

        for(var i = 0; i < numberOfMocks ; i++){

          var match = {};
          
          var doc = {};
          doc.id = ++counter;
          doc.title = utils.randomString(12);
          doc.link = 'http://' + utils.randomString(16);
          doc.description = utils.randomString(64);

          match.doc = doc;
          match.confidence = (Math.round(Math.random()*10000)/100);
          match.rank = ++index;
          match.click = false
          match.feedback = {};
          match.feedback.rating = 1;
          match.feedback.comments = utils.randomString(32);
          
          outcome.matches.push(match);
        }
        return outcome;
      };

      
      var getMatches = function(query) {
        // perform some asynchronous operation, resolve or reject the promise when appropriate.
        return $q(function(resolve, reject) {
          setTimeout(function() {
            var objs = getMockAnswers(name);
            if (true) {
              resolve(objs);
            } else {
              reject('exception');
            }
          }, 1000);
        });
      }

        return { getQuestionMatches: getMatches };

    } 
  )
  ;


