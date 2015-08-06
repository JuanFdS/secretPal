'use strict';

angular.module('secretPalApp')
  .factory('Account', function($http, $rootScope) {
    return {
      getProfile: function() {
        return $http.get('https://secret-inlet-3742.herokuapp.com/auth/me').then(function(data) { $rootScope.loggedUser = data; return data;});
      },
      getCurrentProfile: function() {
        return $rootScope.loggedUser.data;
      }
    };
  });
