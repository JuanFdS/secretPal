'use strict';

var app = angular.module('secretPalApp')
    .controller('WishlistController', function ($scope, $modal, $log) {
        $scope.wishlist = [
            {worker: "Pepe", gift: "ahhhhh"},
            {worker: "Jose", gift: "ahhhh2h"}
        ];

        $scope.Edit = function (wish) {
            var modalInstance = $modal.open({
                animation: false,
                templateUrl: 'editModalWish.html',
                controller: 'ModalInstanceCtrl',
                resolve: {
                    wish: function () {
                        return angular.copy(wish);
                    }
                }
            });
            modalInstance.result.then(function (returnedWish) {
                angular.copy(returnedWish, wish);
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

        $scope.Delete = function (wish) {
            $scope.wishlist.splice(
                $scope.wishlist.indexOf(wish), 1
            );
        }
    })
    .controller('ModalInstanceCtrl', function ($scope, $modalInstance, wish) {
        $scope.wish = wish;

        $scope.ok = function () {
            $modalInstance.close($scope.wish);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    });