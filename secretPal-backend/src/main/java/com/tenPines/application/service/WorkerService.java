package com.tenPines.application.service;

import com.tenPines.model.Worker;
import com.tenPines.persistence.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    public Worker save(Worker newWorker) {
        return workerRepository.save(newWorker);
    }
}
