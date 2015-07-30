'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $rootScope, $auth, Account) {

    Account.getProfile().success(function(data){
      $rootScope.loggedWorker = data
    });

  $scope.isAuthenticated = function() {
    return $auth.isAuthenticated();
  };

  $scope.isAdmin = function(){
    return $rootScope.loggedWorker.admin;
  }
});
