'use strict';

var app = angular.module('secretPalApp');
app.controller('FriendRelationController', function($scope, $modal, $filter, FriendRelationService, SweetAlert) {

  FriendRelationService.all( function(data) {
    $scope.friendRelations = data;
    $scope.posibilities = $scope.friendRelations.map( function(relation){ return relation.giftGiver; });

    $scope.relations = $filter('filter')($scope.friendRelations, {giftReceiver:null});
  });

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
    SweetAlert.swal({
      title: "Actualizando",
      text: "Esto puede tardar un rato...\n muchos algotimos",
      showConfirmButton: false
    });
    FriendRelationService.new($scope.friendRelations);
  };

  $scope.auto = function(){
    shuffleArray($scope.posibilities);
    console.log($scope.posibilities.map(function(worker){ return worker.fullName; }));

    $scope.posibilities.forEach(function(worker, index) {
      $scope.friendRelations.find(function(relation){
        return relation.giftGiver == worker;
      }).giftReceiver = $scope.posibilities[(index+1) % $scope.posibilities.length ];
    });
  };

  $scope.clean = function (relation) {
    relation.giftReceiver = null;
  };

});

var shuffleArray = function(array) {
  var m = array.length, t, i;

  // While there remain elements to shuffle
  while (m) {
    // Pick a remaining element…
    i = Math.floor(Math.random() * m--);

    // And swap it with the current element.
    t = array[m];
    array[m] = array[i];
    array[i] = t;
  }

  return array;
}

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

  $scope.auto = function(){
    angular.forEach($scope.relations, function(relation) {
      if(relation.giftReceiver === null) {

        var arr = $filter('filter')($scope.friendRelations, $scope.notUsed(relation.giftGiver.id));
        arr = $filter('filter')(arr, function (who) {
          return who.giftGiver !== relation.giftGiver;
        });

        var whatYouWant = arr[Math.floor(Math.random() * arr.length)].giftGiver;

        relation.giftReceiver = whatYouWant;
      }
    })
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});*/
