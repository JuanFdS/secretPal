'use strict';

var app = angular.module('secretPalApp');
app.controller('WorkersController', function($scope, $modal, WorkerService, FriendRelationService, $filter, $location) {

    WorkerService.all(function(data){ $scope.workers = data;});
    FriendRelationService.all( function(data) {$scope.participants = data;})

    $scope.delete = function (worker) {
      if (worker.wantsToParticipate) {
        alert("This worker is participating. You cant delete it");
      } else {
          WorkerService.delete(worker.id, function() {
              $scope.workers = $filter('filter')($scope.workers, {id: '!' + worker.id})
          });
      }
    };

    $scope.Reset = function () {
      $scope.newName = '';
      $scope.newMail = '';
      $scope.newDate = '';
    };

    $scope.Add = function () {
      var newWorker = buildWorker();
      WorkerService.new(newWorker, function(persistedWorker) {
        $scope.workers.push(persistedWorker);
      });
      $scope.Reset();
      $("#add_worker").collapse('hide');
    };

    function buildWorker() {
      return {fullName: $scope.newName, eMail: $scope.newMail, dateOfBirth: $scope.newDate,  wantsToParticipate: false}
    };

    $scope.changeIntention = function (worker) {
      var keepGoing = true;
      angular.forEach($scope.participants, function(participant) {
          if (keepGoing){
            if (worker.id === participant.giftGiver.id && participant.giftReceiver !== null ) {
              alert("This worker has a secretpal associated. Please remove it before stop participating");
              worker.wantsToParticipate = true;
              keepGoing = false;
            }
            if (participant.giftReceiver !== null && worker.id === participant.giftReceiver.id) {
              alert("This worker is a participant's secretpal. Please remove it before stop participating");
              worker.wantsToParticipate = true;
              keepGoing = false;
            }
          }
        }
      );

      if(keepGoing) {WorkerService.changeIntention(worker);}

    };

    $scope.assignation = function() {
      $location.path('/friendRelations')
    }

    /*DATEPICKER FUNCTIONS*/
    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
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









