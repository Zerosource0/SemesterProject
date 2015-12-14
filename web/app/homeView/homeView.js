'use strict';

angular.module('myApp.homeView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/homeView', {
                    templateUrl: 'app/homeView/homeView.html',
                    controller: 'homeViewCtrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('homeViewCtrl', ['$http', '$scope', '$filter', function ($http, $scope, $filter) {
                $scope.searching = false;
                var seats = 1;
                $scope.passengers = {};

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

                $scope.bookFlight = function () {
                    
                    $scope.booking = true;
                    if (seats > 1) {
                        $scope.extraPassengers = true;
                    }

                };
                $scope.getData = {};
                $scope.dataProcessor = function(){
                    console.log($scope.getData.line);
                    console.log($scope.getData.flights);
                }
                
               $scope.passengerFormatter = function(){
                    var finalString="";
                    angular.forEach($scope.passengers, function(value, key){
                        finalString = finalString +value.firstName()+"-"+value.lastName();
                    })
                    console.log(finalString);
                }
                
                $scope.reserveFlightTickets = function(){
                    console.log($scope.passengers);
                    console.log("airline"+$scope.getData.line.airline);
                    console.log("flightId"+$scope.getData.flight.totalPrice);
                    $http.post("api/reservation/" + $scope.getData.line.airline + "/" + $scope.getData.flight.flightID 
                            + "/" + $scope.getData.flight.flightDate + "/" + seats + "/" + $scope.getData.flight.travelTime
                            + "/" + $scope.getData.flight.totalPrice + "/" + $scope.getData.flight.origin + "/" 
                            + $scope.getData.flight.destination + "/" + $scope.getData.passengers)
                            .success(function (data, status) {
                                if (status != 200) {
                                    $scope.toException = data;
                                    $scope.loading = "";
                                } else {
                                    $scope.loading = "";
                                    $scope.data = data;
                                }
                            }).error(function (data, status) {

                    });
                }

                http://localhost:8080/semesterSeedSP/api/reservation/testAirline/COL2216x100x2016-02-01T15:00:00.000Z/2016-02-01T15:00:00.000Z/2/90/100/CPH/SXF/Marc-Jesse,Adam-Lewandowski

                        $scope.seatAmount = function () {
                            console.log("seats" + seats);
                            return new Array(parseInt(seats - 1));
                        };

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
                    $scope.searching = false;
                    $scope.booking = false;
                    $scope.loading = "Searching for flights.."
                    $scope.searching = true;
                    $scope.exception = "";
                    
                    seats = $scope.searcht.seats;
                    var year = $scope.searcht.date.getFullYear();
                    var month = $scope.searcht.date.getMonth();
                    var day = $scope.searcht.date.getDate();
                    var date = new Date(year, month, day, 2);

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
                    $scope.booking = false;
                    $scope.searching = true;
                    $scope.loading = "Searching for flights.."
                    $scope.exception = "";
                    seats = $scope.searcht.seats;

                    var year = $scope.searcht.date.getFullYear();
                    var month = $scope.searcht.date.getMonth();
                    var day = $scope.searcht.date.getDate();
                    var date = new Date(year, month, day, 2);

                    $http.get("api/flightinfo/" + $scope.searcht.from + "/" + $scope.searcht.to + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                            .success(function (data, status) {
                                if (status != 200) {
                                    $scope.loading = "";
                                    $scope.exception = data;
                                    console.log(date.toISOString());
                                } else {
                                    $scope.loading = "";
                                    $scope.data = data;
                                    console.log(date.toISOString());
                                }
                            }
                            ).error(function (data, status) {
                        $scope.error = data;
                    });

                };

                $scope.searchWithAirlineFrom = function () {
                    $scope.booking = false;
                    $scope.searching = true;
                    $scope.loading = "Searching for flights.."
                    $scope.exception = "";
                    seats = $scope.searcht.seats;
                    var year = $scope.searcht.date.getFullYear();
                    var month = $scope.searcht.date.getMonth();
                    var day = $scope.searcht.date.getDate();
                    var date = new Date(year, month, day, 2);

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
                    $scope.booking = false;
                    $scope.searching = true;
                    $scope.loading = "Searching for flights.."
                    $scope.exception = "";
                    seats = $scope.searcht.seats;
                    var year = $scope.searcht.date.getFullYear();
                    var month = $scope.searcht.date.getMonth();
                    var day = $scope.searcht.date.getDate();
                    var date = new Date(year, month, day, 2);

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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/SXF/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
                            .success(function (data, status) {
                                if (status != 200) {
                                    $scope.loading = "";
                                    $scope.exception = data;
                                } else {
                                    $scope.loading = "";
                                    $scope.data = data;
                                    console.log(data);
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/AMS/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/BCN/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/HND/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/BCN/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/STN/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
                            .success(function (data, status) {
                                if (status != 200) {
                                    $scope.loading = "";
                                    $scope.exception = data;
                                    console.log(date.toISOString());

                                } else {
                                    $scope.loading = "";
                                    $scope.data = data;
                                    console.log(date.toISOString());
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/DME/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
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
                    var wrongMonth = date.getUTCMonth();
                    var wrongDay = date.getUTCDate();
                    var wrongYear = date.getUTCFullYear();
                    var formattedDate = new Date(wrongYear, wrongMonth, wrongDay, 25);
                    var day = formattedDate.getUTCDate();
                    var month = formattedDate.getUTCMonth() + 1;
                    var year = formattedDate.getUTCFullYear();

                    $http.get("api/flightinfo/CPH/JFK/" + year + "-" + month + "-" + day + "T00:00:00.000Z/1")
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


            }]);
    