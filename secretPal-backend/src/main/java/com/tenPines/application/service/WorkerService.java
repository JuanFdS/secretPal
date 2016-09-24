package com.tenPines.application.service;

import com.tenPines.model.Worker;
import com.tenPines.persistence.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    public Worker save(Worker newWorker) {
        return workerRepository.save(newWorker);
    }

    public List<Worker> getAllParticipants() {
        Worker example = new Worker();
        example.setWantsToParticipate(true);
        return workerRepository.findAll(Example.of(example));
    }
}
