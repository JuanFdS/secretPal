'use strict';

angular.module('secretPalApp').service('FriendRelationService', function($http, SweetAlert) {

  function buildRoute(path) {
    var route = 'https://secret-inlet-3742.herokuapp.com/friendRelation';
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
        errorMsg("No se pudo procesar el pedido");
      });
  };

  this.new = function(idGiver, idReceiver, unSuccessFunction) {
    $http.post(buildRoute('/' + idGiver + '/' + idReceiver)).
      success(function() {
        successMsg("La asignacion fue exitosa");
      }).
      error(function() {
        errorMsg("No se pudo procesar el pedido");
        unSuccessFunction();
      });
  };

  this.delete = function(idGiver, idReceiver, successFunction) {
    $http.delete(buildRoute('/' + idGiver + '/' + idReceiver)).
      success(function() {
        successFunction();
      }).
      error(function() {
        errorMsg("No se pudo borrar esta relacion");
      });
  };

  this.getFriend = function(worker, callback) {
    return $http.post(buildRoute('/friend'), worker).
        then(function(data) {
          callback(data);
        },
        function() {
          errorMsg("Intente nuevamente");
        });
  };

});
