'use strict';

angular.module('myApp.reservationsView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {

  $routeProvider.when('/reservationsView', {
    templateUrl: 'app/reservationsView/reservationsView.html',
    controller: 'reservationsViewCtrl',
    controllerAs: 'ctrl'
  });
}]).controller('reservationsViewCtrl', function ($http, $scope) {
          $http({
            method: 'GET',
            url: 'api/demouser'
          }).then(function successCallback(res) {
            $scope.data = res.data.message;
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
          });

          $http.get('api/currency/dailyrates')
                    .success(function (data, status, headers, config) {
                        $scope.rates = data;
                    })
                    .error(function (data, status, headers, config) {
                        console.log(status + " " + headers + " " + data);
                    });
                    
                    
          $scope.convertSwitch = function(){
              var a = $scope.fromCurrency;
              $scope.fromCurrency = $scope.toCurrency;
              $scope.toCurrency = a;
          };
          
          $scope.convert = function(){
              $http({
                    method: 'GET',
                    url: 'api/currency/calculator/' + $scope.amount +"/"+ $scope.fromCurrency +"/"+ $scope.toCurrency
                }).then(function successCallback(response) {
                    console.log(response);
                    $scope.result = response.data.convertedCurrency;         
                }, function errorCallback(response) {
                    console.log(response);
                });
              
          };
                    
        });