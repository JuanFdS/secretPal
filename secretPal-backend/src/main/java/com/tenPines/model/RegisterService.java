package com.tenPines.model;


import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public User registerUser(NewUser aNewUser) {
        validateIfUserNameHasBeenUsed(aNewUser.getUserName());
        Worker worker = validateIfCorrectWorker(aNewUser.getEmail());
        User user = User.newUser(worker, aNewUser.getUserName(), aNewUser.getPassword());
        userService.save(user);
        return user;
    }

    private void validateIfUserNameHasBeenUsed(String userName) {
        if (userService.retrieveUserByUserName(userName) == null) {
        }
        throw new RuntimeException(RegisterService.messageWhenUserNameHasAlreadyBeenUsed());
    }

    private Worker validateIfCorrectWorker(String email) {
        if (workerService.retrieveWorkerByEmail(email) == null) {
            throw new RuntimeException(RegisterService.messageWhenNotIsAWorker());
        } else {
            return workerService.retrieveWorkerByEmail(email);
        }
    }

    private static String messageWhenUserNameHasAlreadyBeenUsed() {
        return "The user name has already been used";
    }

    private static String messageWhenNotIsAWorker() {
        return "The email does not belong to a worker";
    }
}
