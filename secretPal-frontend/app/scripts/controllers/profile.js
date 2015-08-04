'use strict';

angular.module('secretPalApp')
  .controller('ProfileController', function($scope, user, $location, FriendRelationService, WishlistService, SweetAlert) {

    FriendRelationService.getFriend(user.data.worker, function(friend){
        $scope.friend = friend;

        if(friend.data === ""){
          $location.path('/');
          SweetAlert.swal("No estas participando","avisale al administrador", "error");
        }

          WishlistService.getAllWishesFor($scope.friend.data, function(wishlist){
              $scope.wishlist = wishlist;
          });
    });
  });
