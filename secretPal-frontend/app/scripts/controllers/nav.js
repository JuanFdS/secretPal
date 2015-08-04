'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $rootScope, $auth, Account) {




  $scope.isAuthenticated = function() {
    return $auth.isAuthenticated();
  };

  $scope.isAdmin = function(){
    return Account.getCurrentProfile().admin;
  };
});
