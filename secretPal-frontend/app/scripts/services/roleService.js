'use strict';

angular.module('secretPalApp').service('RoleService', function($http) {

    function buildRoute(path) {
      var route = 'http://localhost:9090/role';
      return route + path;
    }

    this.checkForAdmPrivileges = function() {
      $http.get(buildRoute('/')).
        success(function(data) {
          return data;
        })
    }

  }
);

