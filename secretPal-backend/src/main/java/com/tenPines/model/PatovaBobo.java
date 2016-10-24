package com.tenPines.model;


/**
 * Created by Kevin on 19/10/16.
 */

public class PatovaBobo implements Patova {

    @Override
    public String enterWith(Credential aCredential){
        if(!(aCredential.getUserName().equals("kevin"))){
            throw new RuntimeException(errorMessageWhenUserIsInvalid());
        }
        return aCredential.getUserName();
    }

    static String errorMessageWhenUserIsInvalid() {
        return "The user is not registered";
    }

}
