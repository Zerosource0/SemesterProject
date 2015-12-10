'use strict';

angular.module('myApp.homeView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/homeView', {
                    templateUrl: 'app/homeView/homeView.html',
                    controller: 'homeViewCtrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('homeViewCtrl', function ($http, $scope,$filter) {
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

            $scope.searchSelector = function () {

                if (angular.isUndefined($scope.searcht.to) || $scope.searcht.to === "" || $scope.searcht.to === null) {
                    if (angular.isUndefined($scope.searcht.airline) || $scope.searcht.airline === "" || $scope.searcht.airline === null || $scope.searcht.airline === "All") {
                        $scope.searchFrom();
                    } else {
                        $scope.searchWithAirlineFrom();
                    }
                } else {
                    if (angular.isUndefined($scope.searcht.airline) || $scope.searcht.airline === "" || $scope.searcht.airline === null || $scope.searcht.airline === "All") {
                        $scope.searchFromTo();
                    } else {
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
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
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
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
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

                $http.get("api/airline/" + $scope.searcht.airline + "/" + $scope.searcht.from + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
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

                $http.get("api/airline/" + $scope.searcht.airline + "/" + $scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };

            $scope.popularBerlin = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/SXF/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularAmsterdam = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/AMS/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularBarcelona = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/BCN/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularTokyo = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/HND/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularRome = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/BCN/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;

                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularLondon = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/STN/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                           }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularMoscow = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/DME/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };
            
             $scope.popularNewYork = function () {
                $scope.searching = true;
                $scope.loading = "Searching for flights.."
                $scope.exception = "";
                var date = new Date($filter('date')(new Date(), 'MM/dd/yy'));

                $http.get("api/flightinfo/CPH/JFK/" + date.toISOString() + "T01:00:00.000Z/1")
                        .success(function (data, status) {
                            if (status != 200) {
                                $scope.loading = "";
                                $scope.exception = data;
                            } else {
                                $scope.loading = "";
                                $scope.data = data;
                            }
                        }
                        ).error(function (data, status) {
                    $scope.error = data;
                });

            };


        });
    