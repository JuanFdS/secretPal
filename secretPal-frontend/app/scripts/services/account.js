'use strict';

angular.module('secretPalApp')
  .factory('Account', function($http) {
    return {
      getProfile: function() {
        return $http.get('http://localhost:9090/auth/me');
      }};
  });
