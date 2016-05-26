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
    ,'mgcrea.ngStrap'
    ,'ui.bootstrap'
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
  })
  .service( 'utils', 
    function (){

      var randomString = function(length) {
        var chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var result = '';
        for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
        return result;
      };

      return {
        randomString: randomString
      };

    } 
  )
  .service( 'answers', function (utils, $q, $resource){

      var inget = function(query){
          var os = [];
          for(var i = 0; i < 16; i++){
            var o = {};

            o.title = utils.randomString(12);
            o.link = 'http://' + utils.randomString(16);
            o.description = utils.randomString(64);
            o.confidence = (Math.round(Math.random()*10000)/100);
            o.rating = 1;
            o.comments = utils.randomString(32);
            os.push(o);
          }
          return os;
      };
      
      var asyncGet = function(query) {
        // perform some asynchronous operation, resolve or reject the promise when appropriate.
        return $q(function(resolve, reject) {
          setTimeout(function() {
            var objs = inget(name);
            if (true) {
              resolve(objs);
            } else {
              reject('exception');
            }
          }, 1000);
        });
      }

        return { get: asyncGet };

    } 
  )
  ;
