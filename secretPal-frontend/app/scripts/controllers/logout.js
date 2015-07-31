'use strict';

angular.module('secretPalApp')
  .controller('LogoutController', function($auth) {

    if (!$auth.isAuthenticated()) {
      return;
    }
    $auth.logout()
      .then(function() {

        /*$alert({
          content: 'You have been logged out',
          animation: 'fadeZoomFadeDown',
          type: 'material',
          duration: 3
        });*/
      });
  });