package com.tenPines.builder;

import com.tenPines.model.User;
import com.tenPines.model.Worker;
import com.tenPines.persistence.UserRepository;
import com.tenPines.persistence.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * Created by tenpines on 04/04/17.
 */
public class UserFactory {
    public static UserRepository userRepository;
    public static WorkerRepository workerRepository;

    public static void setRepositories(UserRepository usersRepo, WorkerRepository workersRepo){
        userRepository = usersRepo;
        workerRepository = workersRepo;
    }

    public static User newUser(){
        Worker worker = new WorkerBuilder().withFullName("Test").withEmail("test@test.com").withBirthDayDate(LocalDate.now().withYear(1999)).build();
        workerRepository.save(worker);
        User user = User.newUser(worker, "Test", "111");
        userRepository.save(user);
        return user;
    }
}
