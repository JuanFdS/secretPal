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
        if((userService.userNameAvailable(aCredential.getUserName())) ||
                userService.validatePassword(aCredential.getUserName(),aCredential.getPassword())){
            throw new RuntimeException(errorMessageWhenUserIsInvalid());
        }
        return aCredential.getUserName();
    }

    static String errorMessageWhenUserIsInvalid() {
        return "The user is not registered";
    }

}
