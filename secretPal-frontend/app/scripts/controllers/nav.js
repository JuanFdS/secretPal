'use strict';

angular.module('secretPalApp')
  .controller('navCtrl', function($scope, $rootScope, Account, Token) {

  $scope.getUserName = function () {
    return Token.getToken();
  };

  $scope.isAuthenticated = function() {
    return (Token.isAuthenticated());
  };

  $scope.isAdmin = function(){
    return Account.getCurrentProfile().admin;
  };

});
