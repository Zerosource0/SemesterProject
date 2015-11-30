'use strict';

angular.module('myApp.companyInfoView', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/companyInfoView', {
              templateUrl: 'app/companyInfoView/companyInfoView.html',
              controller: 'companyInfoViewCtrl',
              controllerAs: 'ctrl'
            });
          }])

        .controller('companyInfoViewCtrl',['$http', '$scope', 'DisableAuthInterceptor', function ($http, $scope, DisableAuthInterceptor) {
            
            $scope.searchInput;
            $scope.searchClicked = false;
            
          
          
          $scope.submitSearch = function(){
             DisableAuthInterceptor.enableLoader = false;
          $http.get('http://cvrapi.dk/api?search='+ $scope.searchInput.search + 
                  '&country='+ $scope.searchInput.country)
                  .success(function(data){
                      $scope.result = data;
                      $scope.companyList = data.productionunits;
                      $scope.searchClicked = true;
                   DisableAuthInterceptor.enableLoader = true;
                  })
                  .error(function(data, status){
                      console.log("Error: data");
                      console.log(data);
                      console.log("status");
                      console.log(status);
                      DisableAuthInterceptor.enableLoader = true;
                  });
  
                  
          };
        }]);