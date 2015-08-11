'use strict';

angular.module('secretPalApp').service('WorkerService', function($http, SweetAlert) {

  function buildRoute(path) {
    var route = 'https://secret-inlet-3742.herokuapp.com/worker';
    return route + path;
  }

  function successMsg(msg) {
    SweetAlert.swal("", msg, "success");
  }

  function errorMsg(msg) {
    SweetAlert.swal("Algo salio mal",msg, "error");
  }


  this.all = function(callback) {
    $http.get(buildRoute('/')).
      success(function(data) {
        callback(data);
      }).
      error(function() {
        errorMsg("Intentelo denuevo mas tarde");
      });
  };

  this.new = function(worker, successFunction) {
    $http.post(buildRoute('/'), worker).
      success(function(data) {
        successFunction(data);
        successMsg("Este pino fue creado exitosamente");
      }).
      error(function() {
        errorMsg("No se pudo crear este pino.");
      });
  };

  this.changeIntention = function(worker) {
    $http.post(buildRoute('/intention'), worker).
        success(function() {
        });
  };

  this.delete = function(id, successFunction) {
    $http.delete(buildRoute('/' + id)).
        success(function() {
          successMsg("Pino eliminado exitosamente");
          successFunction();
        });
  };

  this.adminMail = function() {
    $http.get(buildRoute('/adminMail'))
  }

});
