package com.tenPines.model;


import com.tenPines.restAPI.SecurityToken;

/**
 * Created by Kevin on 19/10/16.
 */

public class Patova {

    public String enterWith(Credential aCredential){
        return aCredential.getUserName();
    }

}
