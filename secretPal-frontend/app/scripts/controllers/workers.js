'use strict';

/**
 * @ngdoc function
 * @name secretPalApp.controller:WorkersCtrl
 * @description
 * # AboutCtrl
 * Controller of the secretPalApp
 */
angular.module('secretPalApp')
  .controller('WorkersController', ['$scope', function($scope) {

    $scope.history = [];

    $scope.workers = [
      { name: 'Toia', mail: 'toia@10pines.com', date:'040515'  },
      { name: 'Maria', mail: 'maria@10pines.com', date: '040899' }
    ];

    $scope.Delete = function (index) {
      if ($scope.history.length === 10)
        $scope.history.shift();
      $scope.history.push($scope.workers[index]);
      $scope.workers.splice(index, 1);
    };

    $scope.Reset = function () {
      $scope.newName = '';
      $scope.newMail = '';
      $scope.newDate = '';
    }

    $scope.Add = function () {
      if (!$scope.newName)
        return;

      $scope.workers.push({
        name: $scope.newName,
        mail: $scope.newMail,
        date: $scope.newDate,
      });
      $scope.Reset();
    }

    $scope.Undo = function () {
      $scope.workers.push($scope.history[ $scope.history.length - 1 ]);
      $scope.history.pop();
    }

  }]);

