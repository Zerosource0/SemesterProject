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
             $scope.searching = false;
     
            $http({
                method: 'GET',
                url: "api/airport/"
            }).then(function successCallback(res) {
                $scope.airports = res.data;

            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });
            
              $http({
                method: 'GET',
                url: "api/airline/"
            }).then(function successCallback(res) {
                $scope.airlines = res.data;

            }, function errorCallback(res) {
                $scope.error = res.status + ": " + res.data.statusText;
            });
            
            $scope.searchSelector = function(){
                
                if(angular.isUndefined($scope.searcht.to)|| $scope.searcht.to === "" || $scope.searcht.to === null){
                    if(angular.isUndefined($scope.searcht.airline) || $scope.searcht.airline === "" || $scope.searcht.airline === null || $scope.searcht.airline === "All"){
                        console.log("Search From");
                        $scope.searchFrom();
                    }else{
                        console.log("Search Airline from");
                        $scope.searchWithAirlineFrom();  
                    }
                }else{
                    if(angular.isUndefined($scope.searcht.airline)|| $scope.searcht.airline === "" || $scope.searcht.airline === null || $scope.searcht.airline === "All"){
                        console.log("Search From To");
                        $scope.searchFromTo();
                    }else{
                        console.log("Search Airline From To, " + $scope.searcht.airline);
                        $scope.searchWithAirlineFromTo();  
                    }
            }

            }
        
            $scope.searchFrom = function () {
                $scope.loading = "Searching for flights.."
                $scope.searching = true;
                $scope.exception = "";
                var year = $scope.searcht.date.getFullYear();
                var month = $scope.searcht.date.getMonth();
                var day = $scope.searcht.date.getDate();
                var date = new Date(year, month, day, 1);
              


                $http.get("api/flightinfo/" + $scope.searcht.from + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.toException = data;
                                $scope.loading = "";
                                console.log("res:" + $scope.searchf.from + "/" + date.toISOString() + "/" + $scope.searchf.seats);
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                                console.log("res200:" + data);
                                console.log("res:" + $scope.searchf.from + "/" + date.toISOString() + "/" + $scope.searchf.seats);
                            }
                        }
                        ).error(function (data, status) {
                    console.log(data);
                    $scope.error = data;
                });

            };

            $scope.searchFromTo = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var year = $scope.searcht.date.getFullYear();
                var month = $scope.searcht.date.getMonth();
                var day = $scope.searcht.date.getDate();
                var date = new Date(year, month, day, 1);

                $http.get("api/flightinfo/" + $scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                                console.log("res:" + data);
                                console.log($scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats);
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                                console.log($scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats);
                                console.log(data.toString());

                            }
                        }
                        ).error(function (data, status) {
                    console.log(data);
                    $scope.error = data;
                });

            };
            
             $scope.searchWithAirlineFrom = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var year = $scope.searcht.date.getFullYear();
                var month = $scope.searcht.date.getMonth();
                var day = $scope.searcht.date.getDate();
                var date = new Date(year, month, day, 1);

                $http.get("api/flightinfo/" + $scope.searcht.airline + "/" +$scope.searcht.from + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                                console.log("res:" + data);
                                console.log($scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats);
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                                console.log($scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats);
                                console.log(data.toString());

                            }
                        }
                        ).error(function (data, status) {
                    console.log(data);
                    $scope.error = data;
                });

            };
            
             $scope.searchWithAirlineFromTo = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var year = $scope.searcht.date.getFullYear();
                var month = $scope.searcht.date.getMonth();
                var day = $scope.searcht.date.getDate();
                var date = new Date(year, month, day, 1);

                $http.get("api/flightinfo/" + $scope.searcht.airline + "/" +$scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                                console.log("res:" + data);
                                console.log($scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats);
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                                console.log($scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats);
                                console.log(data.toString());

                            }
                        }
                        ).error(function (data, status) {
                    console.log(data);
                    $scope.error = data;
                });

            };


            
        });
    