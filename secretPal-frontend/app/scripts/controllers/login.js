'use strict';

angular.module('secretPalApp')
    .controller('LoginController', function($scope, $auth) {

        $scope.authenticate = function(provider) {
            $auth.authenticate(provider);
        };

    });