'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $rootScope, Account, $location) {

  $scope.isAuthenticated = function() {
    debugger;
    return Account.isAuthenticated();
  };

  $scope.isAdmin = function(){
    if(Account.isAuthenticated()) {
      return $rootScope.loggedUser.admin
    }
  };

  $scope.logout = function () {
    debugger;
    Account.logout();
    Account.isAuthenticated();
    return $location.path('/');
  };

});
