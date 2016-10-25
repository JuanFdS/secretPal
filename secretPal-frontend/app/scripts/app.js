'use strict';

angular
  .module('secretPalApp', [
    'ui.bootstrap',
    'ngAnimate',
    'ngMessages',
    'ngRoute',
    'satellizer',
    'oitozero.ngSweetAlert',
    'toggle-switch'
  ])
  .config(function ($routeProvider) {
    var authenticated = function (Account, $location, Token) {
      if (!Token.isAuthenticated()) {
        $location.path('/login');
      }
      return Account.getProfile();
    };

    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainController'
      })
      .when('/mail', {
        templateUrl: 'views/mail.html',
        controller: 'MailController',
        resolve: { user : authenticated }
      })
      .when('/workers', {
        templateUrl: '../views/workers.html',
        controller: 'WorkersController',
        resolve: { user : authenticated }
      })
      .when('/friendRelations', {
        templateUrl: '../views/friendRelations.html',
        controller: 'FriendRelationController',
        resolve: { user : authenticated }
      })
      .when('/wishlist', {
        templateUrl: '../views/wishlist.html',
        controller: 'WishlistController',
        resolve: { user : authenticated }
      })
      .when('/login', {
        templateUrl: '../views/login.html',
        controller: 'LoginController'
      })
      .when('/logout', {
        templateUrl: '../views/main.html',
        controller: 'LogoutController',
        resolve: { user : authenticated }
      })
      .when('/profile', {
        templateUrl: '../views/profile.html',
        controller: 'ProfileController',
        resolve: { user : authenticated }
      });

    // $authProvider.google({
    //   clientId: '136089227578-tq2gjl89s5b27dk2sdpacbb2a7m6gha9.apps.googleusercontent.com',
    //   url: '/api/auth/google',
    //   authorizationEndpoint: 'https://accounts.google.com/o/oauth2/auth',
    //   redirectUri: window.location.origin || window.location.protocol + '//' + window.location.host,
    //   hd: '10pines.com',
    //   scope: ['profile', 'email'],
    //   scopePrefix: 'openid',
    //   scopeDelimiter: ' ',
    //   requiredUrlParams: ['scope'],
    //   optionalUrlParams: ['display', 'hd'],
    //   display: 'popup',
    //   type: '2.0'
    // });
  });
