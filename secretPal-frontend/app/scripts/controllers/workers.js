'use strict';

/**
 * @ngdoc function
 * @name secretPalApp.controller:WorkersCtrl
 * @description
 * # AboutCtrl
 * Controller of the secretPalApp
 */
var app = angular.module('secretPalApp');
app.controller('WorkersController', function ($scope, $modal) {

    $scope.history = [];
    $scope.workers = [
      { name: 'Toia', mail: 'toia@10pines.com', date: 'Oct 29, 1990', participating: false, secretpal: null  },
      { name: 'Roman', mail: 'roman@10pines.com', date: 'Oct 29, 1990', participating: true, secretpal: null  },
      { name: 'Joaquin', mail: 'joaquin@10pines.com', date: 'Oct 29, 1990', participating: false, secretpal: null  },
      { name: 'Maria', mail: 'maria@10pines.com', date: '662321623906', participating: true, secretpal: null }
    ];
    $scope.Delete = function (index) {
      if ($scope.workers[index].participating) {
        alert("This worker is participating. You cant delete it");
        return;
      }

      if ($scope.history.length === 10){
        $scope.history.shift();
      }
      $scope.history.push($scope.workers[index]);
      $scope.workers.splice(index, 1);
    };

    $scope.RemovePal = function (index) {
      $scope.workers[index].secretpal = null;
    };

    $scope.Reset = function () {
      $scope.form.$setPristine();
      $scope.newName = '';
      $scope.newMail = '';
      $scope.newDate = '';
    };
    $scope.Add = function () {
      if (!$scope.newName || !$scope.newMail){
        return;
      }
      $scope.workers.push({
        name: $scope.newName,
        mail: $scope.newMail,
        date: $scope.newDate
      });
      $scope.Reset();
      $("#add_worker").collapse('hide');
    };
    $scope.Undo = function () {
      $scope.workers.push($scope.history[ $scope.history.length - 1 ]);
      $scope.history.pop();
    };

    $scope.Change = function (index) {
      if ($scope.workers[index].secretpal !== null) {
        alert("This worker has a secretpal associated. Please remove it before stop participating");
        $scope.workers[index].participating = true;
        return;
      }
      /*TODO si alguien te tiene como secretPal tmp podes dejar de participar*/
    };

    /*DATEPICKER FUNCTIONS*/
    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };

    $scope.Open = function() {
      /*alert("Hello! I am an alert box!!");*/
      var modalInstance = $modal.open({
        animation: false,
        templateUrl: '../../views/pal_assignment_modal.html',
        controller: 'pal_assignmentCtrl',
        resolve: {
          workers: function () {
            return $scope.workers;
          }
        }
      });
      modalInstance.result.then(function (updatedWorkers) {
        $scope.workers = updatedWorkers;
      });
  };
});

app.controller('pal_assignmentCtrl', function ($scope, $modalInstance, workers) {

  $scope.participants = workers;
  /*$scope.secretpals = $filter('filter')(participants, {participating:true});*/

/*  $scope.notUsed = function (participant) {
    var isUsed = false;
    angular.forEach(workers, function(worker) {

      if (worker.secretpal === participant) {
        if (worker !== participant) {isUsed = true};
      }

    });
    return (!isUsed);
  }*/

  $scope.ok = function () {
    $modalInstance.close($scope.participants);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});

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

