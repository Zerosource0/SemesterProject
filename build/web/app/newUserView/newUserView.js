'use strict';

angular.module('myApp.newUserView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/newUserView', {
                    templateUrl: 'app/newUserView/newUserView.html',
                    controller: 'AppNewUserCtrl'
                });
            }])

        .controller('AppNewUserCtrl', ['$http', '$scope', function ($http, $scope)
            {
                $scope.submitNewUser = function()
                {
                    $http.post('api/newuser', {username: $scope.newUser.username, 
                        password: $scope.newUser.password, role: $scope.newUser.role, 
                        firstName: $scope.newUser.firstName, lastName: $scope.newUser.lastName,
                    email: $scope.newUser.email, phone: $scope.newUser.phone})
                            .success(function (data)
                            {
                                console.log(data);
                                $scope.success = "You have successully registered";
                            })
                            .error(function (data, status)
                            {
                                console.log("ERROR: " + data, status);
                                $scope.success = "You did something wrong, error: " + status;
                            });
                }
                ;
            }]);