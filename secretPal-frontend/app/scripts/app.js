'use strict';

angular
  .module('secretPalApp', [
    'ui.bootstrap',
    'ngAnimate',
    'ngMessages',
    'ngRoute',
    'satellizer',
    'mgcrea.ngStrap'
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
      .when('/logout', {
        templateUrl: '../views/main.html',
        controller: 'LogoutController'
      })
      .when('/profile', {
        templateUrl: '../views/profile.html',
        controller: 'ProfileController',
        resolve: {
          authenticated: function($q, $location, $auth) {
            var deferred = $q.defer();

            if (!$auth.isAuthenticated()) {
              $location.path('/login');
            } else {
              deferred.resolve();
            }
            return deferred.promise;
          }
        }
      })
      .otherwise({
        redirectTo: '/'
      });

    $authProvider.google({
      clientId: '136089227578-tq2gjl89s5b27dk2sdpacbb2a7m6gha9.apps.googleusercontent.com',
      url: 'http://localhost:9090/auth/google',
      authorizationEndpoint: 'https://accounts.google.com/o/oauth2/auth',
      redirectUri: window.location.origin || window.location.protocol + '//' + window.location.host,
      hd: '10pines.com',
      scope: ['profile', 'email'],
      scopePrefix: 'openid',
      scopeDelimiter: ' ',
      requiredUrlParams: ['scope'],
      optionalUrlParams: ['display', 'hd'],
      display: 'popup',
      type: '2.0'
    });
  })

  .controller('navCtrl', function($scope, $auth) {
/*    $scope.navClass = function (page) {
      var currentRoute = $location.path().substring(1) || 'home';
      return page === currentRoute ? 'active' : '';
    };*/

    $scope.isAuthenticated = function() {
      return $auth.isAuthenticated();
    };
  });
