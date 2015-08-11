'use strict';

angular.module('secretPalApp')
    .controller('MainController', function($scope, WorkerService) {

      WorkerService.adminMail( function(mail) { $scope.adminMail = mail;});
    });
