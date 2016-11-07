'use strict';

angular.module('secretPalApp')
  .controller('GiftDefaultController', function($scope, GiftDefaultService,  user, $location, SweetAlert, WorkerService) {

    GiftDefaultService.all(function(data){
      debugger;
      $scope.giftsDefault = data;
    });


  });

