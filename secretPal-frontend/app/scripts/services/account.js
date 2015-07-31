'use strict';

angular.module('secretPalApp')
  .factory('Account', function($http, $rootScope) {
    return {
      getProfile: function() {
        return $http.get('http://localhost:9090/auth/me').then(function(data) { $rootScope.loggedUser = data; return data;});
      },
      getCurrentProfile: function() {
        return $rootScope.loggedUser.data;
      }
    };
  });
