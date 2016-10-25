package com.tenPines.model;


import com.tenPines.configuration.AdminProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    public Worker worker;

    @NotNull
    public String userName;

    @NotNull
    public String password;

    public User(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }


    static public User newUser(Worker aWorker, String aUserName, String aPassword){
        User user = new User();
        user.setWorker(aWorker);
        user.setUserName(aUserName);
        user.setPassword(aPassword);
        return user;
    }


    public boolean isAdmin() throws IOException {
        return worker.geteMail().equals(AdminProperties.getAdminEmail());
    }
}