'use strict';

angular.module('secretPalApp').service('MailService', function($http, SweetAlert) {

  function buildRoute(path) {
    var route = 'https://secret-inlet-3742.herokuapp.com/mail';
    return route + path;
  }

  function successMsg(msg) {
    SweetAlert.swal("", msg, "success");
  }

  function errorMsg(msg) {
    SweetAlert.swal("Algo salio mal",msg, "error");
  }


  this.get = function(successFunction) {
    $http.get(buildRoute('/')).
      success(function(data) {
        successFunction(data);
      }).
      error(function() {
        errorMsg("Intentelo denuevo mas tarde");
      });
  };

  this.new = function(mail) {
    $http.post(buildRoute('/'), mail).
      success(function() {
        successMsg("Se ha actualizado la configuracion del mail");
      }).
      error(function() {
        errorMsg("Intentelo de nuevo mas tarde");
      });
  };

});
