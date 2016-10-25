'use strict';

angular.module('secretPalApp')
  .controller('LogoutController', function(Token, SweetAlert) {

    if (!Token.isAuthenticated()) {
      return;
    }
    Token.logout()
      .then(function(){
        SweetAlert({
          content: 'You have been logged out',
          animation: 'fadeZoomFadeDown',
          type: 'material',
          duration: 3
        });
      });
  });
