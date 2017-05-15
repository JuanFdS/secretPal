package com.tenPines.model;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RelationsScavenger {
    private WorkerService workerService;
    private FriendRelationService friendRelationService;

    public RelationsScavenger(WorkerService workerService, FriendRelationService friendRelationService) {
        this.workerService = workerService;
        this.friendRelationService = friendRelationService;
    }

    public List<Worker> findWorkersWithUnassignedBirthdayOn(LocalDate date) {

        return workerService.getAllParticipants().stream()
                .filter(worker ->
                        worker.getBirthday().withYear(date.getYear()).equals(date)
                )
                .filter(worker ->
                        !friendRelationService.workerHasRelationForYear(worker, date.getYear())
                )
                .collect(Collectors.toList());
    }
}
