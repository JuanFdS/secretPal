'use strict';

angular.module('secretPalApp')
  .controller('ProfileController', function($scope, user, $location, FriendRelationService, WishlistService) {

    FriendRelationService.getFriend(user.data.worker, function(friend){
        $scope.friend = friend;

        if(friend.data == ""){
          $location.path('/');
          alert("No estas participando, avisale al administrador");
        }

          WishlistService.getAllWishesFor($scope.friend.data, function(wishlist){
              $scope.wishlist = wishlist;
          });
    });
  });
