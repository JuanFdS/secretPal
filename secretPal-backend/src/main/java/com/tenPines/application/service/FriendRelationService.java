package com.tenPines.application.service;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import com.tenPines.persistence.FriendRelationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRelationService {
    private final FriendRelationRepository friendRelationRepository;
    private final WorkerService workerService;

    public FriendRelationService(FriendRelationRepository friendRelationRepository, WorkerService workerService) {
        this.friendRelationRepository = friendRelationRepository;
        this.workerService = workerService;
    }

    public boolean workerHasRelationForYear(Worker worker, int currentYear) {
        return ! friendRelationRepository.findByGiftReceiverAndScheduledDate(
                worker, worker.getBirthday().withYear(currentYear))
                .isEmpty();

    }

    public Integer countTimeHasGivenAPresent(Worker worker) {
        // TODO intelliJ dice que new Integer no es nescesario... es cierto?
        return new Integer(friendRelationRepository.findByGiftGiverAndWasFulfilledTrue(worker).size());
    }

    public Integer countTimeHasReceivedAPresent(Worker worker) {
        // TODO intelliJ dice que new Integer no es nescesario... es cierto?
        return new Integer(friendRelationRepository.findByGiftReceiverAndWasFulfilledTrue(worker).size());
    }

    public FriendRelation save(FriendRelation friendRelation) {
        return friendRelationRepository.save(friendRelation);
    }

    public List<FriendRelation> getAllRelations() {
        return friendRelationRepository.findAll();
    }

    public void autoAssignRelations() {
        throw new RuntimeException("Not implemented");
    }
}
