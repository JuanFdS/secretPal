'use strict';

angular.module('secretPalApp')
    .controller('LoginController', function($scope, $auth, $alert, $location) {

        $scope.authenticate = function(provider) {

          $auth.authenticate(provider)
           .then(function() {
              var myAlert = $alert({
                content: 'You have successfully logged in',
                placement: 'top',
                type: 'material',
                show: true,
                duration: 3
              });
              $location.path('/profile');
            }).
           catch(function(response) {
              $alert({
                content: response.data ? response.data.message : response,
                animation: 'fadeZoomFadeDown',
                type: 'material',
                duration: 3
              });

           });
        };

        $scope.isAuthenticated = function() {
          return $auth.isAuthenticated();
        };


    });
