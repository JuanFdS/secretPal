'use strict';

angular.module('secretPalApp')
    .controller('LoginController', function($scope, $auth, $location, SweetAlert) {

        $scope.authenticate = function(provider) {

          SweetAlert.swal({
            title: "Logeandose",
            text: "Entrando en la Matrix...",
            showConfirmButton: false
          });

          $auth.authenticate(provider)
           .then(function() {
              SweetAlert.swal("¡Bienvenido!", "Ingresaste correctamente", "success");
              $location.path('/profile');
            }).
           catch(function() {
             $auth.logout();
             SweetAlert.swal("No estas registrado", "Pongase en contacto con el administrador", "error");
           });
        };
    });
