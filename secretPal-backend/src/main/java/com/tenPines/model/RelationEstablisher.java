package com.tenPines.model;

import com.tenPines.application.clock.Clock;
import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class RelationEstablisher {

    private WorkerService workerService;
    private FriendRelationService friendRelationService;

    public RelationEstablisher(WorkerService workerService, FriendRelationService friendRelationService) {
        this.workerService = workerService;
        this.friendRelationService = friendRelationService;
    }


    public FriendRelation buildRelationFor(Worker reciever, int year) {
        Worker giftGiver = workerService.getAllParticipants().stream()
                .filter(worker -> ! worker.equals(reciever))
                .max((worker, otherWorker) -> {
                    Integer workerScore = worker.giverRelations.size() - worker.receiverRelations.size();
                    Integer otherWorkerScore = otherWorker.giverRelations.size() - otherWorker.receiverRelations.size();
                    return workerScore.compareTo(otherWorkerScore);
                }).get();

        return new FriendRelation(giftGiver, reciever, year);
    }
}
