'use strict';

angular.module('myApp.reservationsView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {

  $routeProvider.when('/reservationsView', {
    templateUrl: 'app/reservationsView/reservationsView.html',
    controller: 'reservationsViewCtrl',
    controllerAs: 'ctrl'
  });
}]).controller('reservationsViewCtrl', function ($http, $scope) {
          
                    
});