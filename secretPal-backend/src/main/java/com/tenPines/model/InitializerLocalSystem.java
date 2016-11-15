package com.tenPines.model;


import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.persistence.UserRepository;

import java.time.LocalDate;

public class InitializerLocalSystem {
    private Worker kevin = new Worker("kevin varela", "kevin.varela@10pines.com", LocalDate.of(1993,11,26), true);
    private Worker aye = new Worker("ayelen garcia", "ayelen.garcia@10pines.com", LocalDate.of(1992,12,26), true);
    private Worker joaco = new Worker("joaquin azcarate", "joaquin.azcarate@10pines.com", LocalDate.of(1990,12,16), true);
    private Worker gustavo = new Worker("gustavo crespi", "gustavo.crespi@10pines.com", LocalDate.of(1990,12,30), true);
    private Worker gian = new Worker("gian fioriello", "gian.fioriello@10pines.com", LocalDate.of(1990,12,24), true);
    private User userKevin = User.newUser(kevin,"kevin","1234");
    private WorkerService workerService;
    private UserService userService;

    public InitializerLocalSystem(WorkerService aWorkerService, UserService aUserService) {
        this.workerService = aWorkerService;
        this.userService = aUserService;
        this.init();
    }

    void init() {
        workerService.removeAll();
        workerService.save(kevin);
        workerService.save(aye);
        workerService.save(joaco);
        workerService.save(gustavo);
        workerService.save(gian);
        userService.save(userKevin);
    }
}
