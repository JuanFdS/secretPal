package com.tenPines.model;


import com.tenPines.application.service.WorkerService;

import java.time.LocalDate;

public class InitializerLocalSystem {
    public  Worker kevin = new Worker("kevin", "kevin.varela@10pines.com", LocalDate.of(1993,4,6), true);
    public  Worker aye = new Worker("aye", "ayelen.garcia@10pines.com", LocalDate.of(1992,3,24), true);
    private WorkerService workerService;

    public InitializerLocalSystem(WorkerService workerService) {
        this.workerService = workerService;
        this.init();
    }

    void init() {
        workerService.removeAll();
        workerService.save(kevin);
        workerService.save(aye);
    }
}