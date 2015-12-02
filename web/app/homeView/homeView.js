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




            $scope.searchFrom = function () {

                
                var year = $scope.search.date.getFullYear();
                var month = $scope.search.date.getMonth();
                var day = $scope.search.date.getDate();
                var date = new Date(year, month, day, 1);


                $http({
                    method: 'GET',
                    url: "api/flightinfo/" + $scope.search.from + "/" + date.toISOString() + "/" + $scope.search.seats
                }).then(function successCallback(res) {
                    $scope.data = res.data;

                }, function errorCallback(res) {
                    $scope.error = res.status + ": " + res.data.statusText;
                });
            }


        });