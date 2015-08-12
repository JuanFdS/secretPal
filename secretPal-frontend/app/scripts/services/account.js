'use strict';

angular.module('secretPalApp')
  .factory('Account', function($http, $rootScope, SweetAlert) {
    function buildRoute(path) {
      var route = 'https://secret-inlet-3742.herokuapp.com/auth';
      return route + path;
    }
    var loggedUser;

    return {
      getProfile: function() {
        return $http.get(buildRoute('/me'))
                    .then(function(data){
                        loggedUser = data;
                        return data;
                    });
      },
      getCurrentProfile: function() {
        return loggedUser.data;
      },
      getCurrentAdmin: function(){
        return $http.get(buildRoute('/admin'))
      },
      setCurrentAdmin: function(admin){
        return $http.post(buildRoute('/admin'), admin).then(function(){
            SweetAlert.swal("Cambio de Admin", "El nuevo Admin es: " + admin.fullName, "success");
        }, function(data){
          SweetAlert.swal("Algo salio mal",data, "error");
        });
      }
    };
  });
