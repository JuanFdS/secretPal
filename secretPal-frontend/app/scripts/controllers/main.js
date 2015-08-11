'use strict';

angular.module('secretPalApp')
    .controller('MainController', function($scope, WorkerService) {
      $scope.adminMail = WorkerService.adminMail();
    });
