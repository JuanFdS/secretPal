'use strict';

angular.module('secretPalApp')
  .controller('LoginController', function ($scope, SweetAlert, Account) {
    $scope.userName = "";
    $scope.password = "";

    $scope.internalAuthenticate = function () {

      SweetAlert.swal({
        title: "Logeandose",
        text: "Entrando en la Matrix...",
        showConfirmButton: false
      });

      Account.login({userName: $scope.userName, password: $scope.password});
    };
  });


    // $scope.authenticate = function (provider) {
    //
    //   SweetAlert.swal({
    //     title: "Logeandose",
    //     text: "Entrando en la Matrix...",
    //     showConfirmButton: false
    //   });
    //
    //   $auth.authenticate(provider)
    //     .then(function () {
    //       SweetAlert.swal("¡Bienvenido!", "Ingresaste correctamente", "success");
    //       $location.path('/profile');
    //     }).catch(function () {
    //     $auth.logout();
    //     SweetAlert.swal("No estas registrado", "Pongase en contacto con el administrador", "error");
    //   });
    // };
