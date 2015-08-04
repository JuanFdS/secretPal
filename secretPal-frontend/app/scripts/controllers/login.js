'use strict';

angular.module('secretPalApp')
    .controller('LoginController', function($scope, $auth, $location, SweetAlert) {

        $scope.authenticate = function(provider) {

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
