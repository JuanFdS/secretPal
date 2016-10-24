'use strict';

var app = angular.module('secretPalApp');

app.controller('MailsFailureController', function($scope, $route, MailService, SweetAlert) {



     MailService.all(function(data){ $scope.messageFailure = data;});




});