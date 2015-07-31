'use strict';

angular.module('secretPalApp')
  .controller('ProfileController', function($scope, user, FriendRelationService, WishlistService) {

    FriendRelationService.getFriend(user.data.worker, function(friend){
        $scope.friend = friend;
          WishlistService.getAllWishesFor($scope.friend.data, function(wishlist){
              $scope.wishlist = wishlist;
          });
    });
  });
