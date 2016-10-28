'use strict';

angular.module('secretPalApp')
  .factory('Account', function($http, $rootScope, SweetAlert, $location, Token) {
    function buildRoute(path) {
      var route = '/api/auth';
      return route + path;
    }
    var loggedUser;

    return {
      getProfile: function() {
        return $http.get(buildRoute('/me'), {
          headers: {
            Authorization: Token.getToken()
          }
        }).then(function(response){
            loggedUser = response;
            return response;
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
        var self = this;
        return $http.post(buildRoute('/login'), credentials).then(function (response) {  //TODO: ESTE USER QUE RECIBO ME SIRVE PARA PONER QUE ESTOY AUTENTICADO
          Token.saveToken(response.data.token);
          SweetAlert.swal("¡Bienvenido!", "Ingresaste correctamente", "success"),
          $location.path('/profile');
        }).catch(function () {
          Token.logout();
          SweetAlert.swal("No estas registrado", "Pongase en contacto con el administrador", "error");
          $location.path('/login');
        })
      },

      register: function (newUser) {
        var self = this;
        return $http.post(buildRoute('/register'), newUser).then(function () {
          SweetAlert.swal("¡Registrado correctamente!", "Gracias por participar", "success"),
            $location.path('/login');
        }).catch(function () {
          Token.logout();
          SweetAlert.swal("No te has registrado", "Intente nuevamente", "error");
          $location.path('/register');
        })
      }
    };
  });
