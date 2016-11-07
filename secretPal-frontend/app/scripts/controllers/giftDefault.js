'use strict';

angular.module('secretPalApp')
  .controller('GiftDefaultController', function($scope, user, $location, GiftDefaultController, SweetAlert, WorkerService) {

    GiftDefaultController.all(function(data){ $scope.giftsDefault = data;});


  });

