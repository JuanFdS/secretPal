package com.tenPines.persistence;


import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;


public interface SecretPalEventMethods {

     SecretPalEvent retrieveEvent();

    Worker retrieveAssignedFriendFor(Worker participant);

    SecretPalEvent createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver);

    void deleteRelationInEvent(SecretPalEvent event, FriendRelation friendRelation);

    FriendRelation retrieveRelation(Long from, Long to);
}
