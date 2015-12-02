'use strict';

angular.module('myApp.homeView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/homeView', {
                    templateUrl: 'app/homeView/homeView.html',
                    controller: 'homeViewCtrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('homeViewCtrl', function ($http, $scope) {
            $http({
                    method: 'GET',
                    url: "api/airport/"
                }).then(function successCallback(res) {
                    $scope.airports = res.data;

                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });



            $scope.searchFrom = function () { 
                var year = $scope.searchf.date.getFullYear();
                var month = $scope.searchf.date.getMonth();
                var day = $scope.searchf.date.getDate();
                var date = new Date(year, month, day, 1);


                $http({
                    method: 'GET',
                    url: "api/flightinfo/" + $scope.searchf.from + "/" + date.toISOString() + "/" + $scope.searchf.seats
                }).then(function successCallback(res) {
                    $scope.data = res.data;

                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };
            
            $scope.searchFromTo = function () { 
                var year = $scope.searcht.date.getFullYear();
                var month = $scope.searcht.date.getMonth();
                var day = $scope.searcht.date.getDate();
                var date = new Date(year, month, day, 1);


                $http({
                    method: 'GET',
                    url: "api/flightinfo/" + $scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats
                }).then(function successCallback(res) {
                    $scope.data = res.data;

                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            };



        });