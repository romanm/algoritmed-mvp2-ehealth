(function(angular) {
	'use strict';

	var app = angular.module('mvp1App', ['ngSanitize', 'ngFileSaver']);
	app.config(['$compileProvider', function ($compileProvider) {
	  $compileProvider.debugInfoEnabled(false);
	}]);

	app.controller('Mvp1Ctrl', function($scope, $http, $filter, $timeout, Blob) {
		console.log('----Mvp1Ctrl------controller-----');
		$http.get('/f/config/mvp1.algoritmed.medic.config.json').then( function(response) {
			$scope.config = response.data;
		})
		$scope.testData={}
		$scope.testPost= function(){
			console.log($scope.testData);
			$http.post('/r/posttest2', $scope.testData).then(function(response) {
				console.log(response.data);
			});

		}
		$scope.pagePath = window.location.href.split('?')[0].split('/').splice(4);
		$scope.prevousPath = function(){
			var previousUrl = '/';
			if($scope.config && $scope.pagePath[0]){
				if(!$scope.config[$scope.pagePath[0]] 
				|| 'home' == $scope.config[$scope.pagePath[0]].parent
				){
					//root
				}else{
					previousUrl = '/v/' + $scope.config[$scope.pagePath[0]].parent;
				}
			}
			return previousUrl;
		}
	});

})(window.angular);