'use strict';

angular
  .module('secretPalApp', [
    'ngAnimate',
    'ngRoute',
    'satellizer',
    'ui.bootstrap'
  ])
  .config(function ($routeProvider, $authProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html'
      })
      .when('/workers', {
        templateUrl: '../views/workers.html',
        controller: 'WorkersController'
      })
      .when('/friendRelations', {
        templateUrl: '../views/friendRelations.html',
        controller: 'FriendRelationController'
      })
      .when('/wishlist', {
            templateUrl: '../views/wishlist.html',
            controller: 'WishlistController'
          })
      .when('/login', {
        templateUrl: '../views/login.html',
        controller: 'LoginController'
      })
      .otherwise({
        redirectTo: '/'
      });

      $authProvider.google({
      clientId: '136089227578-tq2gjl89s5b27dk2sdpacbb2a7m6gha9.apps.googleusercontent.com',
      url: 'http://localhost:9090/auth/google',
      authorizationEndpoint: 'https://accounts.google.com/o/oauth2/auth',
      redirectUri: window.location.origin || window.location.protocol + '//' + window.location.host,
      scope: ['profile', 'email'],
      scopePrefix: 'openid',
      scopeDelimiter: ' ',
      requiredUrlParams: ['scope'],
      optionalUrlParams: ['display'],
      display: 'popup',
      type: '2.0'
    });
  })

  .controller('navCtrl', ['$scope', '$location', function ($scope, $location, $auth) {
    $scope.navClass = function (page) {
      var currentRoute = $location.path().substring(1) || 'home';
      return page === currentRoute ? 'active' : '';
    };

  }])
