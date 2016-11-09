'use strict';

angular.module('secretPalApp')
  .controller('GiftDefaultController', function($scope, GiftDefaultService) {

    GiftDefaultService.all(function(data){
      $scope.giftsDefault = data;
    });


  });

