'use strict';

angular.module('myApp.documentationView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/documentationView', {
              templateUrl: 'app/documentationView/documentationView.html',
//              controller: 'documentationViewCtrl',
//              controllerAs: 'ctrl'
            });
          }]);

//        .controller('documentationViewCtrl', function ($http, $scope) {
//          $http({
//            method: 'GET',
//            url: 'api/demouser'
//          }).then(function successCallback(res) {
//            $scope.data = res.data.message;
//          }, function errorCallback(res) {
//            $scope.error = res.status + ": "+ res.data.statusText;
//          });
//
//        });