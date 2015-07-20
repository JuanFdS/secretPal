'use strict';

/**
 * @ngdoc function
 * @name secretPalApp.controller:WorkersCtrl
 * @description
 * # AboutCtrl
 * Controller of the secretPalApp
 */
angular.module('secretPalApp')

  .factory('Workers', ['$resource', function($resource) {
    //TODO: Como podria cambiar esta URL de forma linda?
  return $resource('http://localhost:9090/person/:id', null,
    {
      'update': { method:'PUT' }
    });
}])

.controller('WorkersController', function($scope, Workers) {

    $scope.history = [];

    $scope.workers = Workers.query(function () {});
    $scope.initialWorker = {
      fullName: '',
      eMail: '',
      birthdayDate: ''
    };

    $scope.Delete = function (worker) {
      if ($scope.history.length === 10){
        $scope.history.shift();
      }
      $scope.history.push(worker);


      //TODO: Esta mandando un "OPTIONS" y el CORS lo bloquea. es más simple pasar un POST a mano y a la bosta :D
      Workers.remove(worker, function(data){
        console.log(data);
        //TODO: Creo que aca llega solo sihace succes. Habria que dar un error de otro modo
      });

      var index = $scope.workers.indexOf(worker);
      $scope.workers.splice(index, 1);


    };
    $scope.Reset = function () {
      $scope.form.$setPristine();
      $scope.newWorker = angular.copy($scope.initialWorker);;
    };
    $scope.Add = function () {
      if (!$scope.workers == $scope.initialWorker){
        return;
      }

      Workers.save($scope.newWorker)
      $scope.workers.push($scope.newWorker);

      $scope.Reset();
      $("#add_worker").collapse('hide');

    };
    $scope.Undo = function () {
      $scope.workers.push($scope.history[ $scope.history.length - 1 ]);
      $scope.history.pop();
    };

    /*DATEPICKER FUNCTIONS*/
    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };

})

.directive('unique', function() {
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









