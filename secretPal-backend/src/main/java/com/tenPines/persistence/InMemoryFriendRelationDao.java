package com.tenPines.persistence;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class InMemoryFriendRelationDao implements AbstractRepository<FriendRelation> {

    protected static List<FriendRelation> friendRelations = new ArrayList<FriendRelation>();

    public static List<FriendRelation> getFriendRelations() {
        return friendRelations;
    }

    public static void setFriendRelations(List<FriendRelation> friendRelations) {
        InMemoryFriendRelationDao.friendRelations = friendRelations;
    }

    @Override
    public List<FriendRelation> retrieveAll() {
        return friendRelations;
    }

    @Override
    public List save(FriendRelation... friendRelations) {
        Collections.addAll(InMemoryFriendRelationDao.friendRelations, friendRelations);
        return null;
    }

    @Override
    public FriendRelation refresh(FriendRelation friendRelation) {
        return friendRelation;
    }

    @Override
    public void delete(FriendRelation friendRelation) {
        friendRelations.remove(friendRelation);
    }

    @Override
    public FriendRelation findById(Long id) {
        return friendRelations.get(id.intValue() - 1);
    }

    @Override
    public void update(FriendRelation element) {

    }

    @Override
    public List<FriendRelation> retrieveParticipants() {
        return null;
    }

    @Override
    public Worker retrieveAssignedFriendFor(Worker participant) {
        return null;
    }

    @Override
    public FriendRelation createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver) {
        return null;
    }

}
