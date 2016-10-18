package com.tenPines.model;


import com.tenPines.application.service.WorkerService;

import java.time.LocalDate;

class InitializerSystem {
    public  Worker joaco = new Worker("joaco", "joaco.azcarate@10pines.com", LocalDate.now(), true);
    private WorkerService workerService;

    InitializerSystem(WorkerService workerService) {
        this.workerService = workerService;
        this.init();
    }

    void init() {
        if(!workerService.getAllParticipants().contains(joaco)){
        workerService.save(joaco);}
    }
}
