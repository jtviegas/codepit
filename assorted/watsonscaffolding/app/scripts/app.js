'use strict';

/**
 * @ngdoc overview
 * @name watsonscaffoldingApp
 * @description
 * # watsonscaffoldingApp
 *
 * Main module of the application.
 */
angular
  .module('watsonscaffoldingApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
    , 'mgcrea.ngStrap'
  ])
  .controller(
    'AppCtrl', 
    function($scope, $location){

      $scope.pageClass = function(path){
        return (path == $location.path()) ? 'active' : '';
      };

    }
  )
  .config(function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/user', {
        templateUrl: 'views/user.html',
        controller: 'UserCtrl',
        controllerAs: 'user'
      })
      .when('/info', {
        templateUrl: 'views/info.html',
        controller: 'InfoCtrl',
        controllerAs: 'info'
      })
      .otherwise({
        redirectTo: '/'
      });

      $locationProvider.html5Mode({
        enabled: true
      });
  });
