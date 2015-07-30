'use strict';

angular.module('secretPalApp')
    .controller('LoginController', function($scope, $auth, $alert) {

        $scope.authenticate = function(provider) {

          $auth.authenticate(provider)
           .then(function() {
              var myAlert = $alert({
                title: 'Hi!',
                content:'You have successfully logged in',
                placement: 'top',
                type: 'info',
                show: true,
                duration: 3
              });

            }).
           catch(function(response) {
              $alert({
                title: 'Something went wrong',
                content: response.data ? response.data.message : response,
                animation: 'fadeZoomFadeDown',
                type: 'info',
                show: true,
                duration: 3
              });
           });
        };

        $scope.isAuthenticated = function() {
          return $auth.isAuthenticated();
        };


    });
