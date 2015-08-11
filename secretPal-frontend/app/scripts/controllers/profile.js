'use strict';

angular.module('secretPalApp')
  .controller('ProfileController', function($scope, user, $location, FriendRelationService, WishlistService, SweetAlert, WorkerService) {

    FriendRelationService.getFriend(user.data.worker, function(friend){
        $scope.friend = friend;

        if (!user.data.worker.wantsToParticipate){
          wantToParticipateMsg();
        }
        if(friend.data === ""){
          $location.path('/');
          SweetAlert.swal("No tienes ningun amigo asignado","avisale al administrador", "error");
        }

        WishlistService.getAllWishesFor($scope.friend.data, function(wishlist){
            $scope.wishlist = wishlist;
        });
    });

    function wantToParticipateMsg() {
      SweetAlert.swal({
          title: "No estas participando",
          text: "Queres participar?",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "Si!",
          closeOnConfirm: false
        },
        function () {
          WorkerService.changeIntention(user.data.worker);
          SweetAlert.swal("Ahora estas participando!");
        });
    }

  });
