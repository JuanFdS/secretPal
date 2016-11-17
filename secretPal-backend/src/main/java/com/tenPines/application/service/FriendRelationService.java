package com.tenPines.application.service;

import com.tenPines.application.ReminderSystem;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import com.tenPines.model.process.AssignmentFunction;
import com.tenPines.persistence.FriendRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRelationService {
    @Autowired
    public FriendRelationRepository friendRelationRepository;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ReminderSystem reminders;

    public FriendRelation create(Worker friendWorker, Worker birthdayWorker) {
        return friendRelationRepository.save(new FriendRelation(friendWorker, birthdayWorker));
    }

    public List<FriendRelation> autoAssignRelations(){
        List<FriendRelation> relations = new AssignmentFunction(workerService.getAllParticipants()).execute();
        friendRelationRepository.deleteAllRelations();
        return friendRelationRepository.save(relations);
    }

    public List<FriendRelation> getAllRelations() {
        return friendRelationRepository.findAll();
    }

    public Worker retrieveAssignedFriendFor(Worker unWorker){
        FriendRelation aRelation = friendRelationRepository.findBygiftGiver(unWorker);
        if (aRelation == null){
            throw new RuntimeException(messageWhenWorkerNotHasWorkerAssigned());
        }
        return aRelation.getGiftReceiver();
        }

    private String messageWhenWorkerNotHasWorkerAssigned() {
        return "No tenes un worker asignado";
    }

    public void deleteRelationByReceipt(Worker to) {
        FriendRelation relation = friendRelationRepository.findBygiftReceiver(to);
        friendRelationRepository.delete(relation);
    }

    public List<Worker> getAvailablesRelationsTo(Worker workerTo) {
        List<Worker> availablesReceipt = null;
        availablesReceipt.add(friendRelationRepository.findBygiftReceiver(workerTo).getGiftReceiver());
        return availablesReceipt;
    }

}
