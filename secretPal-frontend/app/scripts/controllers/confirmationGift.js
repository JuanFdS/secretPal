'use strict';

var app = angular.module('secretPalApp');
app.controller('ConfirmationGiftController', function ($scope, $modal, $rootScope, WorkerService, $filter, $location, SweetAlert) {

  WorkerService.all(function (data) {
    $scope.workers = data;
  });

  $scope.change = function(worker){

    if(worker.receivedGift === false) {
      SweetAlert.swal({
          title: "¿Esta seguro que desea cambiarlo?",
          text: "El regalo pasara a ser NO entregado",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "Si!",
          closeOnConfirm: true
        },
        function (isConfirm) {
          if(isConfirm) {
            worker.receivedGift = false;
            //TODO ACTUALIZAR BASE
          }
          else{
            worker.receivedGift = true;
            //TODO ACTUALIZAR BASE
          }
        });
    }
  };
});





