package com.tenPines.model;


import com.tenPines.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SecurityGuardBobo implements Patova {

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserService userService;

    @Override
    public String enterWith(Credential aCredential){
        if(notIsAValidUser(aCredential.getUserName()) || notIsAValidPasswordForThisUser(aCredential)){
            throw new RuntimeException(errorMessageWhenUserOrPasswordIsInvalid());
        }
        return aCredential.getUserName();
    }

    private boolean notIsAValidUser(String aUserName) {
        return userService.userNameAvailable(aUserName);
    }

    private boolean notIsAValidPasswordForThisUser(Credential aCredential) {
        return !(userService.validatePassword(aCredential.getUserName(),aCredential.getPassword()));
    }

    static String errorMessageWhenUserOrPasswordIsInvalid() {
        return "The user do not exist or password is invalid";
    }

}
