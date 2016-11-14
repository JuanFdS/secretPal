'use strict';

angular.module('secretPalApp')
.controller('ProfileController', function($scope, $http, user, $location, FriendRelationService, WishlistService, SweetAlert, WorkerService) {

    $scope.wishlist = [];
    $scope.giftDefault = "A";

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
            WorkerService.changeIntention(user.worker);
            SweetAlert.swal("Ahora estas participando!");
            $scope.noFriendAlert();
          } else {
            $location.path('/');
          }
        });
    };
    if (!user.worker.wantsToParticipate) {
      $scope.wantToParticipateMsg();
    } else {
      FriendRelationService.getFriend(user.worker, function (friend) {

        $http.get('/api/auth/giftsDefault').
        success(function(data) {
          $scope.giftDefault = data.giftDefault;
          $scope.amountDefault = data.amountDefault;
          debugger;
        }).
        error(function() {
          errorMsg("Inténtelo denuevo mas tarde");
        });

        $scope.friend = friend;

        if (friend.data === "") {
          $scope.noFriendAlert();
        }

        WishlistService.getAllWishesFor($scope.friend.data, function (wishlistResponse) {
          $scope.wishlist = wishlistResponse.data;
        });
      });
    }
  });
