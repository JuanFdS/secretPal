'use strict';

var app = angular.module('secretPalApp');
app.controller('GameStatusController', function ($scope, $modal, $rootScope, WorkerService, FriendRelationService) {

   FriendRelationService.all( function(data) {
        $scope.friendRelations = data;});

    $scope.hasGiverGiveGift = function (giftGiver) {
        if (giftGiver.giftDateReceived !== null){
            return "Sí"
        }else{
            return "No"
        };

    };


});