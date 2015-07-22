'use strict';

var app = angular.module('secretPalApp')
    .controller('WishlistController', function ($scope, WorkerService, WishlistService, $modal, $log) {
        WishlistService.all(function(data){ $scope.workers = data; debugger; });
        WorkerService.all(function (data) {
          $scope.posibleWorkers = data;
        })

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
    })
.service('WishlistService', function($http) {

  function buildRoute(path) {
    var route = 'http://localhost:9090/wishlist';
    return route + path;
  }

  this.all = function(callback) {
    $http.get(buildRoute('/')).
      success(function(data) {
        callback(data);
      }).
      error(function() {
        alert("Something went wrong, try again later.");
      });
  };

  this.new = function(worker, successFunction) {
    $http.post(buildRoute('/'), worker).
      success(function() {
        alert("The worker was created.");
        successFunction();
      }).
      error(function() {
        alert("Something went wrong, try again later.");
      });
  };

  this.changeIntention = function(worker) {
    $http.post(buildRoute('/intention'), worker).
      success(function() {
        alert('FIne');
      });
  };

  this.delete = function(id, successFunction) {
    $http.delete(buildRoute('/' + id)).
      success(function() {
        successFunction();
      });
  };

});
