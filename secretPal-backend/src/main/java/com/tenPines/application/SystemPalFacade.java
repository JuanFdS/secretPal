package com.tenPines.application;


import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemPalFacade {


    @Autowired
    FriendRelationService friendRelationService;

    @Autowired
    WorkerService workerService;

    public Worker retrieveAssignedFriendFor(Worker participant) {
        return friendRelationService.retrieveAssignedFriendFor(participant);
    }

    public FriendRelation createRelation(Worker giftGiver, Worker giftReceiver) {
        return friendRelationService.create(giftGiver,giftReceiver);
    }

    public void deleteRelation(Long from, Long to) {
        Worker participant = workerService.retriveWorker(to);
        friendRelationService.retrieveAssignedFriendFor(participant);
    }
}
