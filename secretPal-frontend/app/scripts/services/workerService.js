'use strict';

angular.module('secretPalApp').service('WorkerService', function($http) {

  function buildRoute(path) {
    var route = 'http://localhost:9090/worker';
    return route + path;
  }

  this.all = function(callback) {
    $http.get(buildRoute('/')).
      success(function(data) {
        callback(data);
      }).
      error(function() {
        alert("Ha ocurrido un error. No se pudo procesar la solicitud al servidor. Intentelo de nuevo mas tarde");
      });
  };

  this.new = function(worker, successFunction) {
    $http.post(buildRoute('/'), worker).
      success(function(data) {
        successFunction(data);
      }).
      error(function() {
        alert("Ha ocurrido un error. No se pudo procesar la solicitud al servidor. Intentelo de nuevo mas tarde");
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
          successFunction();
        });
  };

});
