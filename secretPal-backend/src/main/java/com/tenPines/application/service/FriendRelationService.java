package com.tenPines.application.service;

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
    private FriendRelationRepository friendRelationRepository;
    @Autowired
    private WorkerService workerService;

    public FriendRelation create(Worker friendWorker, Worker birthdayWorker) {
        return friendRelationRepository.save(new FriendRelation(friendWorker, birthdayWorker));
    }

    public void autoAssignRelations(){
        friendRelationRepository.save(
                new AssignmentFunction(workerService.getAllParticipants()).execute()
        );
    }

    public List<FriendRelation> getAllRelations() {
        return friendRelationRepository.findAll();
    }

    public Worker retrieveAssignedFriendFor(Worker unWorker){return friendRelationRepository.findBygiftReceiver(unWorker);}

}
