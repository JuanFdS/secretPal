package com.tenPines.restAPI;

/**
 * Created by Kevin on 20/10/16.
 */
public class SecurityToken {

    public SecurityToken(){
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    private String userName;

    public static SecurityToken createWith(String userName) {
        SecurityToken token = new SecurityToken();
        token.setUserName(userName);
        return token;
    }
}
