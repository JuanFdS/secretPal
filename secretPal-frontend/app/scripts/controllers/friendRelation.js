'use strict';

var app = angular.module('secretPalApp');
app.controller('FriendRelationController', function($scope, $modal, $filter, FriendRelationService, SweetAlert) {

  FriendRelationService.all( function(data) {$scope.friendRelations = data;});
  $scope.relations = $filter('filter')($scope.friendRelations, {giftReceiver:null});

  function warningMsg(msg){
    SweetAlert.swal({
      title: "",
      text: msg,
      type: "warning"
    });
  }

  $scope.deleteRelation = function (relation) {
    FriendRelationService.delete(relation.giftGiver.id, relation.giftReceiver.id, function() {
        $scope.friendRelations = $filter('filter')($scope.friendRelations, {giftGiver: '!' + relation.giftGiver});
      });
  };

  $scope.notNull = function(relation){
    return (relation.giftReceiver !== null);
  };

  $scope.notUsed = function(giftGiverSelected){
    return function (relation){
      var notUsed = true;
      angular.forEach($scope.friendRelations, function(fr){
        if (fr.giftReceiver !== null) {
          if (fr.giftReceiver.id === relation.giftGiver.id) {
            if (fr.giftGiver.id !== giftGiverSelected) {notUsed = false;}
          }
        }
      });
      return notUsed;
    };
  };

  $scope.ok = function () {
    angular.forEach($scope.relations, function(relation) {
        if (relation.giftReceiver !== null) {
          FriendRelationService.new(relation.giftGiver.id, relation.giftReceiver.id, function() { $scope.error = true; });}}
    );

    if (!$scope.error) {
      $scope.cancel();
    }
  };

  $scope.auto = function(){
    angular.forEach($scope.relations, function(relation) {
      relation.giftReciever = $scope.friendRelations[Math.floor(Math.random() * $scope.relations.length)].giftGiver
    })
  };

  $scope.reset = function () {
    //
  };

  /*$scope.openModalForAssign = function() {
    var modalInstance = $modal.open({
      animation: false,
      templateUrl: '../../views/addFriendRelationModal.html',
      controller: 'addRelationsController',
      resolve: {
        relationsXXX: function () {
          return angular.copy($scope.friendRelations);
        }
      }
    });
    modalInstance.result.then(function () {
      location.reload();
    });
  };*/

});

/*app.controller('addRelationsController', function ($scope, $modalInstance, $filter, FriendRelationService, relationsXXX) {

  $scope.friendRelations = relationsXXX;
  $scope.relations = $filter('filter')($scope.friendRelations, {giftReceiver:null});

  $scope.notUsed = function(giftGiverSelected){
    return function (relation){
      var notUsed = true;
      angular.forEach($scope.friendRelations, function(fr){
        if (fr.giftReceiver !== null) {
          if (fr.giftReceiver.id === relation.giftGiver.id) {
            if (fr.giftGiver.id !== giftGiverSelected) {notUsed = false;}
          }
        }
      });
      return notUsed;
    };
  };

  $scope.ok = function () {

    angular.forEach($scope.relations, function(relation) {
        if (relation.giftReceiver !== null) {
          FriendRelationService.new(relation.giftGiver.id, relation.giftReceiver.id, function() { $scope.error = true; });}}
    );

    if (!$scope.error) {
      $modalInstance.close();
    }
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});*/
