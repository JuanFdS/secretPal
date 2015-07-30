'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $rootScope, $auth) {

  $scope.isAuthenticated = function() {
    return $auth.isAuthenticated();
  };

  $scope.isAdmin = function(){
    return false;
  }
});
