'use strict';

angular.module('secretPalApp')
  .factory('Account', function($http, $rootScope, SweetAlert, $location) {
    function buildRoute(path) {
      var route = '/api/auth';
      return route + path;
    }
    var loggedUser;

    return {
      getProfile: function() {
        return $http.get(buildRoute('/me'))
                    .then(function(user){
                        loggedUser = user;
                        return user;
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
      },

      login: function (credentials) {
        return $http.post('/api/auth/login', credentials).then(function (response) {
          SweetAlert.swal("¡Bienvenido!", "Ingresaste correctamente", "success");
          $location.path('/profile');
        }).catch(function () {
          // $auth.logout();
          SweetAlert.swal("No estas registrado", "Pongase en contacto con el administrador", "error");
        })
      }
    };
  });
