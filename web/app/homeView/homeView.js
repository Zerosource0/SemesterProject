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
                $scope.toException = "";
                $scope.toFromException = "";
                var year = $scope.searchf.date.getFullYear();
                var month = $scope.searchf.date.getMonth();
                var day = $scope.searchf.date.getDate();
                var date = new Date(year, month, day, 1);


                $http.get("api/flightinfo/" + $scope.searchf.from + "/" + date.toISOString() + "/" + $scope.searchf.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.toException = data;
                                console.log("res:" + data);
                            } else {
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    console.log(data);
                    $scope.error = data;
                });

            };

            $scope.searchFromTo = function () {
                $scope.toFromException = "";
                $scope.toException = "";
                var year = $scope.searcht.date.getFullYear();
                var month = $scope.searcht.date.getMonth();
                var day = $scope.searcht.date.getDate();
                var date = new Date(year, month, day, 1);

                $http.get("api/flightinfo/" + $scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.toFromException = data;
                                console.log("res:" + data);
                            } else {
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    console.log(data);
                    $scope.error = data;
                });

            };



        });