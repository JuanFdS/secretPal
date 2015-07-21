'use strict';

angular.module('secretPalApp').service('WorkerService', function($http) {

  function buildRoute(path) {
    var route = 'http://localhost:9090/person';
    return route + path;
  }

  this.all = function(callback) {
    $http.get(buildRoute('/')).
      success(function(data) {
        callback(data);
      }).
      error(function() {
        alert("Something went wrong, try again later.");
      });
  };

  this.new = function(worker, successFunction) {
    $http.post(buildRoute('/'), worker).
      success(function() {
        alert("The worker was created.");
        successFunction();
      }).
      error(function() {
        alert("Something went wrong, try again later.");
      });
  };

});
