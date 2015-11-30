'use strict';

angular.module('myApp.homeView', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/homeView', {
    templateUrl: 'app/homeView/homeView.html',
    controller: 'homeViewCtrl',
    controllerAs : 'ctrl'
  });
}])

.controller('homeViewCtrl', ["InfoFactory","InfoService",function(InfoFactory,InfoService) {
  this.msgFromFactory = InfoFactory.getInfo();
  this.msgFromService = InfoService.getInfo();
}]);