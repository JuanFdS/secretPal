'use strict';

angular.module('secretPalApp').service('FriendRelationService', function($http, SweetAlert) {

  function buildRoute(path) {
    var route = '/api/friendRelation';
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

  this.new = function(relations) {
    $http.post(buildRoute('/'), relations).
      success(function() {
        successMsg("La asignación fue exitosa");
      }).
      error(function() {
        errorMsg("No se pudo procesar el pedido");
      });
  };

  this.start = function() {
    $http.post(buildRoute('/initilizeRelations'))
      .then(function () {
        successMsg("La asignación fue exitosa");
      }).catch(function () {
      errorMsg("No se pudo procesar el pedido");
    })
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
    return $http.get(buildRoute('/friend/' + worker.id)).
        then(function(data) {
          callback(data);
        },
        function() {
          SweetAlert.swal({
            title: "Aun no tienes un pino asignado",
            text: "Comunicate con un administrador",
            imageUrl: 'https://homeopathicassociates.com/wp-content/uploads/2015/07/Oh-no1.jpg'
          });
        });
  };

  this.getAvailableFriend = function(worker,callback){
    return $http.get(buildRoute('/posibleFriend/' + worker.id )).
      then(function (data) {
        callback(data);
      },
      function () {
        errorMsg("Intente nuevamente mas tarde");
    });
  };

});
