'use strict';

angular.module('myApp.homeView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/homeView', {
    templateUrl: 'app/homeView/homeView.html',
    controller: 'homeViewCtrl',
    controllerAs : 'ctrl'
  });
}])

.controller('homeViewCtrl',function($http, $scope) {
         
                
          $scope.searchFrom = function(){
          $http({
            method: 'GET',
            url: "http://angularairline-plaul.rhcloud.com/api/flightinfo/"+$scope.search.from+"/"+$scope.search.date.toISOString()+"/"+$scope.search.seats
          }).then(function successCallback(res) {
            $scope.data = res.data;
            
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
          });
      }
  
  
});