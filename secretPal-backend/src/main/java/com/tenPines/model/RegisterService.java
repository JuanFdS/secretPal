package com.tenPines.model;


import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class RegisterService {

    public RegisterService(){
    }

    @Autowired
    WorkerService workerService;

    @Autowired
    UserService userService;

    public WorkerService getWorkerService() {
        return workerService;
    }

    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public User registerUser(NewUser aNewUser){

        Worker worker = new Worker(aNewUser.getName() + aNewUser.getLastName(), aNewUser.getEmail(),LocalDate.of(aNewUser.getYear(),aNewUser.getMonth(),aNewUser.getDay()), aNewUser.getWantToParticipate());
        workerService.save(worker);
        User user = User.newUser(workerService.retrieveWorkerByEmail(aNewUser.getEmail()), aNewUser.getUserName(), aNewUser.getPassword());
        userService.save(user);
        return user;
    }
}
