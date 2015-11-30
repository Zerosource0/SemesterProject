'use strict';

angular.module('myApp.allUsers', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/allUsers', {
                    templateUrl: 'app/allUsers/AllUsersView.html',
                    controller: 'allUsersCtrl',
                });
            }])

        .controller('allUsersCtrl', function ($http, $scope) {
            $http.get('api/demoadmin')
                    .success(function (data, status, headers, config) {
                        $scope.data = data;
                    })
                    .error(function (data, status, headers, config) {
                        console.log(status + " " + headers + " " + data);
                        
                    });

            $http.get('api/demoadmin/users')
                    .success(function (data, status, headers, config) {
                        $scope.users = data;
                    })
                    .error(function (data, status, headers, config) {
                        console.log(status + " " + headers + " " + data);
                    });

            $scope.delete = function (id) {
                $http({
                    method: 'DELETE',
                    url: 'api/demoadmin/users/' + id
                }).then(function successCallback(response) {
                    console.log(response);
                    
                    $http.get('api/demoadmin/users')
                    .success(function (data, status, headers, config) {
                        $scope.users = data;
                    })
                    .error(function (data, status, headers, config) {
                        console.log(status + " " + headers + " " + data);
                    });
                    
                }, function errorCallback(response) {
                    console.log(response);
                });
            };
        });