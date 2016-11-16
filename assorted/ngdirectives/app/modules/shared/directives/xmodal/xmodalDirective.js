(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:xmodalDirective
	* @description
	* # xmodalDirective
	* Directive of the app
	*/

	angular
		.module('ngdirectives')
		.directive('xmodal', xmodal);

		function xmodal () {

			var directive = {
			    // Add a custom template for modal
			    templateUrl: 'app/modules/shared/directives/xmodal/xmodal.html',
			    restrict: 'E',
			    transclude: true,
			    replace:true,
			    scope:true,
			    link: function(scope, element, attrs) {
			      scope.title = attrs.title;

			      scope.$watch(attrs.visible, function(value){
			        if(value == true)
			          $(element).modal('show');
			        else
			          $(element).modal('hide');
			      });

			      $(element).on('shown.bs.modal', function(){
			        scope.$apply(function(){
			          scope.$parent[attrs.visible] = true;
			        });
			      });

			      $(element).on('hidden.bs.modal', function(){
			        scope.$apply(function(){
			          scope.$parent[attrs.visible] = false;
			        });
			      });
			    }
			};

			return directive;

		}

})();
