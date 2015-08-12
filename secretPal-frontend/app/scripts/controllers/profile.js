'use strict';

angular.module('secretPalApp')
.controller('ProfileController', function($scope, user, $location, FriendRelationService, WishlistService, SweetAlert, WorkerService) {

    $scope.noFriendAlert = function(){
      $location.path('/');
      SweetAlert.swal("No tienes ningun amigo asignado", "avisale al administrador", "error");
    };

    $scope.wantToParticipateMsg = function() {
      SweetAlert.swal({
          title: "No estas participando",
          text: "Queres participar?",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "Si!",
          closeOnConfirm: false
        },
        function (isConfirm) {
          if (isConfirm) {
            WorkerService.changeIntention(user.data.worker);
            SweetAlert.swal("Ahora estas participando!");
            $scope.noFriendAlert();
          }
        });
    };

    if (!user.data.worker.wantsToParticipate) {
      $scope.wantToParticipateMsg();
    } else {
      FriendRelationService.getFriend(user.data.worker, function (friend) {
        $scope.friend = friend;

        if (friend.data === "") {
          $scope.noFriendAlert();
        }

        WishlistService.getAllWishesFor($scope.friend.data, function (wishlist) {
          $scope.wishlist = wishlist;
        });
      });
    }

  });
