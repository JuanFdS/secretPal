package com.tenPines.application;


import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.GiftDefaultService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.GiftDefault;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SystemPalFacade {


    @Autowired
    FriendRelationService friendRelationService;

    @Autowired
    WorkerService workerService;

    @Autowired
    GiftDefaultService giftDefaultService;

    public Worker retrieveAssignedFriendFor(Long Idparticipant) {
        Worker participant = workerService.retriveWorker(Idparticipant);
        return friendRelationService.retrieveAssignedFriendFor(participant);
    }

    public FriendRelation createRelation(Worker giftGiver, Worker giftReceiver) {
        return friendRelationService.create(giftGiver,giftReceiver);
    }

    public void deleteRelation(Long from, Long to) {
        Worker participant = workerService.retriveWorker(to);
        friendRelationService.retrieveAssignedFriendFor(participant);
    }

    public List<GiftDefault> retrieveAllGiftsDefaults() {
        List<GiftDefault> giftDefaults = giftDefaultService.getAll();
        return giftDefaults;
    }

    public void addGiftDefaults(GiftDefault giftDefault) {
        giftDefaultService.addGift(giftDefault);

    }
}
