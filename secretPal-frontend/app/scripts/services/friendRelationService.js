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
        alert("Cannot request get relations");
      });
  };

  this.new = function(idGiver, idReceiver, unSuccessFunction) {
    $http.post(buildRoute('/' + idGiver + '/' + idReceiver)).
      success(function() {
      }).
      error(function() {
        unSuccessFunction();
        alert("Cannot request new relation");
      });
  };

  this.delete = function(idGiver, idReceiver, successFunction) {
    $http.delete(buildRoute('/' + idGiver + '/' + idReceiver)).
      success(function() {
        successFunction();
      }).
      error(function() {
        alert("Cannot request delete relation");
      });
  };

});
