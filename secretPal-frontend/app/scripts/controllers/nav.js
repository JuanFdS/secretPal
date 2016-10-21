'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $rootScope, Account) {

  // $scope.isAuthenticated = function() {
  //   return (!$auth.isAuthenticated());
  // };

  $scope.isAdmin = function(){
    return Account.getCurrentProfile().admin;
  };
});
