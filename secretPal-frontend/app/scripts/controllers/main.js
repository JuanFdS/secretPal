'use strict';

angular.module('secretPalApp')
    .controller('MainController', function($scope, Account) {
      Account.getCurrentAdmin().then(function(admin){
        $scope.admin = admin.data;
      });
});
