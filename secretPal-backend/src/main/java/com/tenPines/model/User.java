package com.tenPines.model;


import com.tenPines.application.service.AdminService;
import com.tenPines.configuration.AdminProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Entity
// This is necessary since 'user' in postgres is a keyword and hibernate
// doesn't escape table names properly
@Table(name="\"user\"")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    public Worker worker;

    @NotEmpty
    public String userName;

    @NotEmpty
    public String password;


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

    public String geteMail(){
        return worker.geteMail();
    }
}