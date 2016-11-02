package com.tenPines.model;


import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.persistence.UserRepository;

import java.time.LocalDate;

public class InitializerLocalSystem {
    public Worker kevin = new Worker("kevin", "ayelen.garcia@10pines.com", LocalDate.of(1993,12,9), true);
    public Worker aye = new Worker("aye", "ayelen.garcia@10pines.com", LocalDate.of(1992,12,2), true);
    public Worker joaco = new Worker("joaco", "ayelen.garcia@10pines.com", LocalDate.of(1990,12,19), true);
    public User userKevin = User.newUser(kevin,"kevin","1234");
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
        userService.save(userKevin);
    }
}
