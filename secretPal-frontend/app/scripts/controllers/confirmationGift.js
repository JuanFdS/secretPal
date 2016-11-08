'use strict';

var app = angular.module('secretPalApp');
app.controller('ConfirmationGiftController', function ($scope, $http, $modal, $rootScope, WorkerService, $filter, $location, user, SweetAlert) {

  WorkerService.all(function (data) {
    $scope.workers = data;
  });

  function buildRoute(path) {
    var route = '/api/auth';
    return route + path;
  }

  $scope.markReceived = function(id){
    var self = this;
    return $http.put(buildRoute('/confirmationGift/' + id), id).then(function (date) {
      SweetAlert.swal("¡Regalo marcado como recibido!","con la fecha de hoy", "success");
      return $location.path('/confirmationGift');
    }).catch(function (error) {
      Token.logout();
      SweetAlert.swal("No te has registrado", error.data.message, "error");
      $location.path('/register');
    });

  };
});





