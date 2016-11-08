'use strict';

var app = angular.module('secretPalApp');
app.controller('GiftDefaultController', function($scope, $route, GiftDefaultService, SweetAlert) {

  GiftDefaultService.get(

    function(defaultGift){
      $scope.defaultGift = defaultGift;});

  $scope.change = function(){
    SweetAlert.swal({
        title: "Estás seguro?",
        text: "El regalo default será modificado",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si!",
        closeOnConfirm: false
      },
      function (isConfirm) {
        if(isConfirm) {
          GiftDefaultService.new($scope.defaultGift);
        }
      });
  };

});


