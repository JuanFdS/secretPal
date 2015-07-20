'use strict';

angular.module('secretPalApp').service('WorkerService', function($http) {

  function buildRoute(path) {
    var route = 'http://localhost:9090/person';
    return route + path;
  }

  this.all = function(callback) {
    $http.get(buildRoute('/all')).
      success(function(data) {
        debugger;
        callback(data);
      }).
      error(function() {
        alert("Something went wrong, try again later.");
      });
  };

  this.new = function(worker, successFunction) {
    $http.post(buildRoute('/new'), worker).
      success(function() {
        alert("The worker was created.");
        successFunction();
      }).
      error(function() {
        alert("Something went wrong, try again later.");
      });
  };

});
