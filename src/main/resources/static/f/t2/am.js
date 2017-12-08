angular.module('myApp', []).controller('userCtrl', function($scope) {
	console.log("----am--start--------------")
	$scope.c = function(){
		console.log("----am--click--c------------")
		console.log(window.location);
		var hash = window.location.hash.split('#')[1];
		console.log(hash);
	}
	$scope.c();
});