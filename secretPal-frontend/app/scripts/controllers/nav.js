'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $auth, Account) {

    Account.getProfile().success(function(data){
      $scope.loggedWorker = data
    });

  $scope.isAuthenticated = function() {
    return $auth.isAuthenticated();
  };
});
