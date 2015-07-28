'use strict';

angular.module('secretPalApp')
    .controller('LoginController', function($scope, $auth) {

        $scope.authenticate = function(provider) {

          $auth.authenticate(provider);
/*              .then(function(response) {
                // Signed In.

                console.log(response);
                //$auth.getToken();

              });*/

        };
    });
