'use strict';

angular.module('secretPalApp').service('GiftDefaultService', function($http, SweetAlert) {

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


  this.get = function(successFunction) {
    $http.get(buildRoute('/giftsDefault')).
    success(function(data) {
      successFunction(data);
    }).
    error(function() {
      errorMsg("Inténtelo denuevo mas tarde");
    });
  };

  this.new = function(giftDefault) {
    $http.post(buildRoute('/giftsDefault'), giftDefault).
    success(function() {
      successMsg("Se ha actualizado el regalo default");
    }).
    error(function() {
      errorMsg("Inténtelo de nuevo mas tarde");
    });
  };

});
