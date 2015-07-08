'use strict';

/**
 * @ngdoc function
 * @name secretPalApp.controller:WorkersCtrl
 * @description
 * # AboutCtrl
 * Controller of the secretPalApp
 */
var app = angular.module('secretPalApp');

app.controller('WorkersController', ['$scope', function($scope) {

    $scope.history = [];
    $scope.workers = [
      { name: 'Toia', mail: 'toia@10pines.com', date: 'Oct 29, 1990', participating: false  },
      { name: 'Maria', mail: 'maria@10pines.com', date: '662321623906', participating: true }
    ];
    $scope.Delete = function (index) {
      if ($scope.history.length === 10){
        $scope.history.shift();
      }
      $scope.history.push($scope.workers[index]);
      $scope.workers.splice(index, 1);
    };
    $scope.Reset = function () {
      $scope.form.$setPristine();
      $scope.newName = '';
      $scope.newMail = '';
      $scope.newDate = '';
    };
    $scope.Add = function () {
      if (!$scope.newName || !$scope.newDate || !$scope.newMail){
        return;
      }
      $scope.workers.push({
        name: $scope.newName,
        mail: $scope.newMail,
        date: $scope.newDate
      });
      $scope.Reset();
    };
    $scope.Undo = function () {
      $scope.workers.push($scope.history[ $scope.history.length - 1 ]);
      $scope.history.pop();
    };
}]);

app.directive('unique', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$validators.unique = function(modelValue) {
        if (ctrl.$isEmpty(modelValue)) {
          return true;
        }

        var result = [];

        angular.forEach(scope.workers, function(worker){
          result.push( worker.name.toUpperCase() );
        });

        return result.indexOf(modelValue.toUpperCase()) === -1;
      };
    }
  };
});

app.controller('AccordionDemoCtrl', function ($scope) {
  $scope.status = {
    isFirstOpen: true,
    isFirstDisabled: false
  };
});

