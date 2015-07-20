'use strict';

/**
 * @ngdoc overview
 * @name secretPalApp
 * @description
 * # secretPalApp
 *
 * Main module of the application.
 */
angular
  .module('secretPalApp', [
    'ngAnimate',
    'ngRoute',
    'ngResource',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/workers', {
        templateUrl: '../views/workers.html',
        controller: 'WorkersController'
      })
      .otherwise({
        redirectTo: '/'
      });
  })

  .controller('navCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.navClass = function (page) {
      var currentRoute = $location.path().substring(1) || 'home';
      return page === currentRoute ? 'active' : '';
    };
  }])

  .config(['$resourceProvider', function($resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
  }]);
;
