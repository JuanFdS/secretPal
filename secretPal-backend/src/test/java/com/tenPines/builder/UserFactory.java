package com.tenPines.builder;

import com.tenPines.model.User;
import com.tenPines.model.Worker;

import java.time.LocalDate;

/**
 * Created by tenpines on 04/04/17.
 */
public class UserFactory {
    public static User newUser(){
        Worker worker = new WorkerBuilder().withFullName("Test").withEmail("test@test.com").withBirthDayDate(LocalDate.now().withYear(1999)).build();
        User user = User.newUser(worker, "Test", "111");
        return user;
    }
}
