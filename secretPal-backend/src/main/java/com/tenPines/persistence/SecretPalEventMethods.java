package com.tenPines.persistence;


import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;

import java.util.List;


public interface SecretPalEventMethods {

     SecretPalEvent retrieveEvent();

    Worker retrieveAssignedFriendFor(Worker participant);

    FriendRelation createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver);

    void deleteRelationInEvent(SecretPalEvent event, FriendRelation friendRelation);

    FriendRelation retrieveRelation(Long from, Long to);

    List<FriendRelation> retrieveAllRelations();
}
