'use strict';

angular.module('secretPalApp')
  .controller('ProfileController', function($scope, user, FriendRelationService, WishlistService) {

    FriendRelationService.getFriend(user.data.worker, function(friend){
        $scope.friend = friend;
          WishlistService.getAllWishesFor($scope.worker, function(wishlist){
              $scope.wishlist = wishlist;
          });
    });
    debugger;



    $scope.getProfile = function () {
      Account.getProfile()
        .success(function (data) {
          $scope.user = data;
        })
        .error(function (error) {
          $alert({
            content: error.message,
            animation: 'fadeZoomFadeDown',
            type: 'material',
            duration: 3
          });
        });
    };
  });
