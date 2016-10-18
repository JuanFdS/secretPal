package com.tenPines.model;


import com.tenPines.application.service.WorkerService;

import java.time.LocalDate;

public class InitializerLocalSystem {
    public  Worker joaco = new Worker("joaco", "joaquin.azcarate@10pines.com", LocalDate.now(), true);
    private WorkerService workerService;

    public InitializerLocalSystem(WorkerService workerService) {
        this.workerService = workerService;
        this.init();
    }

    void init() {
        workerService.removeAll();
        workerService.save(joaco);
    }
}
