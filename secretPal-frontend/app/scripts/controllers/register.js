'use strict';

angular.module('secretPalApp')
  .controller('RegisterController', function ($scope, SweetAlert, Account, $location) {
    $scope.userName = "";
    $scope.password = "";
    $scope.email = "";

    $scope.registerNewUser = function () {

      SweetAlert.swal({
        title: "Registro enviado",
        text: "Espere un momento, por favor",
        duration: 3,
        showConfirmButton: false
      });

      Account.register({userName: $scope.userName, password: $scope.password, email: $scope.email});
    };

    $scope.returnToMain = function() {
      return $location.path("/");
    }

  });
