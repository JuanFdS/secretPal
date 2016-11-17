'use strict';

var app = angular.module('secretPalApp');

app.controller('MailsFailureController', function($scope, $route, MailService,$filter, SweetAlert) {



     MailService.all(function(data){ $scope.messageFailure = data;});

     $scope.send = function (unsentMessage) {
         $scope.messageFailure = $filter('filter')($scope.messageFailure, {id: '!' + unsentMessage.id});

          MailService.resendMessage(unsentMessage);

          };



});