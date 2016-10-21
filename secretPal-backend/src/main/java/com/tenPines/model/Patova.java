package com.tenPines.model;


import com.tenPines.restAPI.SecurityToken;

/**
 * Created by Kevin on 19/10/16.
 */

public class Patova {

    public SecurityToken enterWith(Credential aCredential){
        return SecurityToken.createWith(aCredential.getUserName());
    }

}
