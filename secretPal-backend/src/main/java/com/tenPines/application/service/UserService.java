package com.tenPines.application.service;

import com.tenPines.model.User;
import com.tenPines.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User aUser) {
        return userRepository.save(aUser);
    }

    public User retrieveUserByUserName(String userName) {
        return userRepository.findByUserName(userName).stream().findFirst().orElseThrow(
                () -> new RuntimeException("The user does not exist")
        );
    }

    public boolean userNameAvailable(String userName) {
        return userRepository.findByUserName(userName).isEmpty();
    }

    public boolean validatePassword(String userName, String password) {
        return userRepository.findByUserName(userName).stream().findFirst().get().getPassword().equals(password);
    }
}
