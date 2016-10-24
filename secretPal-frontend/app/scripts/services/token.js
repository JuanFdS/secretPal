'use strict';

angular.module('secretPalApp')
  .factory('Token', function($location) {
    var token;

    return {
      saveToken: function (aToken) {
        token = aToken
      },

      getToken: function () {
        return token;
      },

      isAuthenticated: function () {
        return token != null;
      },

      logout: function () {
        token = null;
        return $location.path('/');
      }
    };
  });


