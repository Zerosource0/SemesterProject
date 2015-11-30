'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'ui.bootstrap',
  'myApp.security',
  'myApp.homeView',
  'myApp.documentationView',
  'myApp.companyInfoView',
  'myApp.exchangeRateApp',
  'myApp.allUsers',
  'myApp.newUserView',
  'myApp.filters',
  'myApp.directives',
  'myApp.factories',
  'myApp.services'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/homeView'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('authInterceptor');
});


