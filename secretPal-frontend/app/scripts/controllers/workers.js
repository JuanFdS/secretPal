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
      { name: 'Toia', mail: 'toia@10pines.com', date: 'Oct 29, 1990'  },
      { name: 'Maria', mail: 'maria@10pines.com', date: '662321623906' }
    ];

    $scope.Delete = function (index) {
      if ($scope.history.length === 10)
        $scope.history.shift();
      $scope.history.push($scope.workers[index]);
      $scope.workers.splice(index, 1);
    };

    $scope.Reset = function () {
      $scope.form.$setPristine();
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

app.directive('unique', function() {
  return {
    require: 'ngModel',
    restrict: 'A',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$validators.unique = function(modelValue, viewValue) {
        if (ctrl.$isEmpty(modelValue))
          return true;

        var result = [];

        angular.forEach(scope.workers, function(worker){
          result.push( worker.name.toUpperCase() )
        });

        return result.indexOf(modelValue.toUpperCase()) === -1;
      };
    }
  };
});

app.directive('dateInput', function(dateFilter) {
    return {
      require: 'ngModel',
      template: '<input type="date"></input>',
      replace: true,
      link: function(scope, elm, attrs, ngModelCtrl) {
        ngModelCtrl.$formatters.unshift(function (modelValue) {
          return dateFilter(modelValue, 'yyyy-MM-dd');
        });

        ngModelCtrl.$parsers.unshift(function(viewValue) {
          return new Date(viewValue);
        });
      },
    };
  });
