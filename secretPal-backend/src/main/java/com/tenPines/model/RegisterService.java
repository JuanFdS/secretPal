package com.tenPines.model;


import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class RegisterService {

    public RegisterService() {
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


    public void registerUser(NewUser aNewUser) {
        validateIfUserNameHasBeenUsed(aNewUser.getUserName());
        validateIfExistAUserWithThisEmail(aNewUser.getEmail());
        Worker worker = workerService.retrieveWorkerByEmail(aNewUser.getEmail());
        User user = User.newUser(worker, aNewUser.getUserName(), aNewUser.getPassword());
        userService.save(user);
    }

    private void validateIfExistAUserWithThisEmail(String email) {
        if(emailIsAsociatedWithAUser(email)){
           throw new RuntimeException(RegisterService.errorMessageWhenEmailInUse());
        }
    }

    private boolean emailIsAsociatedWithAUser(String email) {
        List<User> users = userService.getAllUsers();
        Stream<Worker> workers = users.stream().map(user -> user.getWorker());
        return workers.anyMatch(worker -> worker.geteMail().equals(email));
    }

    private void validateIfUserNameHasBeenUsed(String userName) {
        if(!(userService.userNameAvailable(userName))){
            throw new RuntimeException(RegisterService.messageWhenUserNameHasAlreadyBeenUsed());
        }
    }

    public static String messageWhenUserNameHasAlreadyBeenUsed() {
        return "The user name has already been used";
    }


    public static String errorMessageWhenEmailInUse() {
        return "This email is now in use for other user";
    }
}
