'use strict';

angular.module('secretPalApp').service('giftDefaultService', function($http, SweetAlert) {

  function buildRoute(path) {
    var route = '/api/auth';
    return route + path;
  }

  function successMsg(msg) {
    SweetAlert.swal("", msg, "success");
  }

  function errorMsg(msg) {
    SweetAlert.swal("Algo salió mal",msg, "error");
  }


  this.new = function(giftDefault) {
    $http.post(buildRoute('/giftDefault'), giftDefault).
    success(function() {
      successMsg("Se ha agregado el regalo default correctamente");
    }).
    error(function() {
      errorMsg("Inténtelo de nuevo mas tarde");
    });
  };

  this.all = function(callback) {
    $http.get(buildRoute('/giftDefault') ).
    success(function(data) {
      callback(data);
    }).
    error(function() {
      errorMsg("Inténtelo de nuevo mas tarde");
    });
  };


});
