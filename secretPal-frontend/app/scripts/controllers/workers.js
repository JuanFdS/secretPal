'use strict';

/**
 * @ngdoc function
 * @name secretPalApp.controller:WorkersCtrl
 * @description
 * # AboutCtrl
 * Controller of the secretPalApp
 */
var app = angular.module('secretPalApp');
app.controller('WorkersController', function($scope, WorkerService, $filter) {

    WorkerService.all(function(data){ $scope.workers = data; debugger; });

    $scope.delete = function (worker) {
      if (worker.wantsToParticipate) {
        alert("This worker is participating. You cant delete it");
      } else {
          WorkerService.delete(worker.id, function() {
              $scope.workers = $filter('filter')($scope.workers, {id: '!' + worker.id})
          });
      }
    };

    $scope.RemovePal = function (index) {
      $scope.workers[index].secretpal = '';
    };

    $scope.Reset = function () {
      $scope.form.$setPristine();
      $scope.newName = '';
      $scope.newMail = '';
      $scope.newDate = '';
    };

    $scope.Add = function () {
      var newWorker = buildWorker();
      WorkerService.new(newWorker, function() {
        debugger;
        $scope.workers.push(newWorker);
        $scope.Reset();
        $("#add_worker").collapse('hide');
      });
      $scope.Reset();
      $("#add_worker").collapse('hide');
    };

    function buildWorker() {
      return {fullName: $scope.newName, eMail: $scope.newMail, dateOfBirth: $scope.newDate,  wantsToParticipate: false}
    };

    function parseDate(date) {
      $filter('date')(date, 'yyyy-MM-dd')
    }

    $scope.changeIntention = function (worker) {
        WorkerService.changeIntention(worker);
      /*if ($scope.workers[index].secretpal !== '') {
        alert("This worker has a secretpal associated. Please remove it before stop participating");
        $scope.workers[index].wantsToParticipate = true;
        return;
      }

      var total = $scope.workers.length;
      for (var i=0; i<total; i++)
        if ($scope.workers[i].secretpal === $scope.workers[index]) {
          alert("This worker is a participant's secretpal. Please remove it before stop participating");
          $scope.workers[index].wantsToParticipate = true;
          return;
        }*/
    };

    /*DATEPICKER FUNCTIONS*/
    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };

    $scope.Open = function() {
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









