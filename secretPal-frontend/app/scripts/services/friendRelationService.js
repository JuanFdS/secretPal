'use strict';

angular.module('secretPalApp').service('FriendRelationService', function($http) {

  function buildRoute(path) {
    var route = 'http://localhost:9090/friendRelation';
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

  this.new = function(idGiver, idReceiver, unSuccessFunction) {
    $http.post(buildRoute('/' + idGiver + '/' + idReceiver)).
      success(function() {
      }).
      error(function() {
        unSuccessFunction();
      });
  };

  this.delete = function(idGiver, idReceiver, successFunction) {
    $http.delete(buildRoute('/' + idGiver + '/' + idReceiver)).
      success(function() {
        successFunction();
      }).
      error(function() {
        alert("Ha ocurrido un error. No se pudo procesar la solicitud al servidor. Intentelo de nuevo mas tarde");
      });
  };

  this.getFriend = function(worker, callback) {
    return $http.post(buildRoute('/friend'), worker).
        then(function(data) {
          callback(data);
        },
        function() {
          alert("Ha ocurrido un error. No se pudo procesar la solicitud al servidor. Intentelo de nuevo mas tarde");
        });
  }

});
