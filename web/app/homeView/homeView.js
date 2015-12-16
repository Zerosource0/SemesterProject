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
                angular.element(document).ready(function () {
                    document.getElementById('register').click();
                    $("#lean_overlay").trigger("click");
                });
                $scope.searching = false;
                var seats = 1;
                var passengerString="";
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
                    $("html, body").animate({ scrollTop: 1300 }, 800);
                    $scope.booking = true;
                    if (seats > 1) {
                        $scope.extraPassengers = true;
                    }

                };

                $scope.getData = {};
                
               $scope.passengerFormatter = function(){
                    
                    var count = parseInt(1);
                    angular.forEach($scope.passengers.person, function(value, key){
                        passengerString = passengerString + value.firstName +"-"+ value.lastName;
                        if(parseInt(count)!==parseInt(seats)){
                            passengerString = passengerString +",";
                            count++;
                        }else{
                            passengerString;
                        }
                      });
                };
                
                $scope.registerUser = function(){
                    
                    $http.post("api/newuser/",{ username: $scope.newUser.username, password: $scope.newUser.password, role: "user", firstName: $scope.newUser.firstName
                            , lastName: $scope.newUser.lastName, email: $scope.newUser.email, phone: $scope.newUser.phone})
                            .success(function(data,status){
                                 if (status != 200) {
                                     console.log(data);
                                    console.log("failed: " +status);
                            console.log({ username: $scope.newUser.username, password: $scope.newUser.password, role: "user", 
                                firstName: $scope.newUser.firstName, lastName: $scope.newUser.lastName, email: $scope.newUser.email, 
                                phone: $scope.newUser.phone});
                                } else {
                                    console.log("success: " +status);
                                    $("html, body").animate({ scrollTop: 605 }, 600);
                                }
                            })
                            .error(function(data,status){
                                
                            })
                }
                
                $scope.reserveFlightTickets = function(){
                    console.log("api/reservation/" + $scope.getData.line.airline + "/" + $scope.getData.flight.flightID 
                            + "/" + $scope.getData.flight.date + "/" + seats + "/" + $scope.getData.flight.traveltime
                            + "/" + $scope.getData.flight.totalPrice + "/" + $scope.getData.flight.origin + "/" 
                            + $scope.getData.flight.destination + "/" + passengerString);

                    $http.post("api/reservation/" + $scope.getData.line.airline + "/" + $scope.getData.flight.flightID 
                            + "/" + $scope.getData.flight.date + "/" + seats + "/" + $scope.getData.flight.traveltime
                            + "/" + $scope.getData.flight.totalPrice + "/" + $scope.getData.flight.origin + "/" 
                            + $scope.getData.flight.destination + "/" + passengerString)
                            .success(function (data, status) {
                                if (status != 200) {
                                    console.log("failed: " +status)
                                    $scope.toException = data;
                                    $scope.loading = "";
                                    $scope.completionMessage ="failure"
                                } else {
                                    console.log("success: " +data)
                                    $scope.loading = "";
                                    $scope.data = data;
                                    $scope.booking = false;
                                    $scope.searching = false;
                                    $scope.extraPassengers = false;
                                    $scope.bookingComplete = true;
                                    $scope.bookingSuccess = true;
                                    window.scrollTo(0, 605);
                                    $scope.completionMessage = "Booking complete! For "+seats+"passengers, from "+ $scope.getData.flight.origin+" to " + $scope.getData.flight.destination
                                    + " on the " + $scope.getData.flight.date;
                                }
                            }).error(function (data, status) {

                    });
                }

                        $scope.seatAmount = function () {
                            console.log("seats" + seats);
                            return new Array(parseInt(seats - 1));
                        };

                $scope.searchSelector = function () {
                    
                    window.scrollTo(0, 605);
                    $scope.searchTable = false;
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
                    $scope.allAirlines = true;

                    seats = $scope.searcht.seats;
                    var year = $scope.searcht.date.getFullYear();
                    var month = $scope.searcht.date.getMonth();
                    var day = $scope.searcht.date.getDate();
                    var date = new Date(year, month, day, 2);

                    $http.get("api/flightinfo/" + $scope.searcht.from + "/" + date.toISOString() + "/" + $scope.searcht.seats)
                            .success(function (data, status) {
                                if (status != 200) {
                                    $scope.exception = data;
                                    $scope.loading = "";
                                } else {
                                    $scope.loading = "";
                                    $scope.data = data;
                                    $scope.searchTable = true;
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
                    $scope.allAirlines = true;
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
                                    $scope.searchTable = true;
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
                    $scope.allAirlines = false;
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
                    $scope.allAirlines = false;
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
                    $("html, body").animate({ scrollTop: 605 }, 600);
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
                    $("html, body").animate({ scrollTop: 605 }, 600);
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
                    $("html, body").animate({ scrollTop: 605 }, 600);
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
                    $("html, body").animate({ scrollTop: 605 }, 600);
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
                    $("html, body").animate({ scrollTop: 605 }, 600);
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
                    $("html, body").animate({ scrollTop: 605 }, 600);
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
                    $("html, body").animate({ scrollTop: 605 }, 600);                  
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
                    $("html, body").animate({ scrollTop: 605 }, 600);                  
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
    