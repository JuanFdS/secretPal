'use strict';

angular.module('secretPalApp').service('MailService', function($http, SweetAlert) {

  function buildRoute(path) {
    var route = '/api/mail';
    return route + path;
  }

  function successMsg(msg) {
    SweetAlert.swal("", msg, "success");
  }

  function errorMsg(msg) {
    SweetAlert.swal("Algo salió mal",msg, "error");
  }


  this.get = function(successFunction) {
    $http.get(buildRoute('/')).
      success(function(data) {
        successFunction(data);
      }).
      error(function() {
        errorMsg("Inténtelo denuevo mas tarde");
      });
  };

  this.new = function(mail) {
    debugger;
    $http.post(buildRoute('/'), mail).
      success(function() {
        successMsg("Se ha actualizado la configuración del mail");
      }).
      error(function() {
        errorMsg("Inténtelo de nuevo mas tarde");
      });
  };

});
