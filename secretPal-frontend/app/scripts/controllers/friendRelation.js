'use strict';

var app = angular.module('secretPalApp');
app.controller('FriendRelationController', function($scope, $modal, $filter, FriendRelationService) {

  FriendRelationService.all( function(data) {$scope.friendRelations = data;})

  $scope.deleteRelation = function (relation) {
    FriendRelationService.delete(relation.giftGiver.id, relation.giftReceiver.id, function() {
        $scope.friendRelations = $filter('filter')($scope.friendRelations, {giftGiver: '!' + relation.giftGiver})
      });
  };

  $scope.notNull = function(relation){
    return (relation.giftReceiver !== null);
  };

  $scope.openModalForAssign = function() {
    var modalInstance = $modal.open({
      animation: false,
      templateUrl: '../../views/addFriendRelationModal.html',
      controller: 'pal_assignmentCtrl',
      resolve: {
        friendRelationsCopy: function () {
          return angular.copy($scope.friendRelations);
        }
      }
    });
    modalInstance.result.then(function (newFriendRelations) {
      $scope.friendRelations = newFriendRelations;
      /*FriendRelationService.all( function(data) { $scope.friendRelations = data;})*/
      debugger;
    });
  };

});

app.controller('pal_assignmentCtrl', function ($scope, $modalInstance, $filter, FriendRelationService, friendRelationsCopy) {

  $scope.friendRelations = friendRelationsCopy;
  $scope.relations = $filter('filter')($scope.friendRelations, {giftReceiver:null});

/*  function giftGiverOf(giftReceiver) {
/!*    $scope.la = $filter('filter')($scope.friendRelations, {giftReceiver:{id:giftReceiver.id}})[0].giftGiver;
    debugger;*!/
    return $filter('filter')($scope.friendRelationsCopy, {giftReceiver:{id:giftReceiver.id}})[0].giftGiver;

  };*/

  $scope.notUsed = function(giftGiverSelected){
    return function (relation){
      var notUsed = true;
      angular.forEach($scope.friendRelations, function(fr){
        if (fr.giftReceiver !== null) {
          if (fr.giftReceiver.id === relation.giftGiver.id) {
            if (fr.giftGiver.id !== giftGiverSelected) {
              notUsed = false;
            }
          }
        }
      });
      return notUsed;
    }
  }

  $scope.ok = function () {

    angular.forEach($scope.relations, function(relation) {
        if (relation.giftReceiver !== null) {
          FriendRelationService.new(relation.giftGiver.id, relation.giftReceiver.id, function() { $scope.error = true })}}
    );

    if ($scope.error) {return;}
    else { $modalInstance.close($scope.friendRelations); }
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});
