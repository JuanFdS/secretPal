package com.tenPines.model;

public class UserForFrontend {

    private Long id;

    public Worker worker;

    public String userName;

    public Boolean admin;


    public UserForFrontend(Long id, Worker worker, String userName,Boolean admin){
        setId(id);
        setWorker(worker);
        setUserName(userName);
        setAdmin(admin);
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setWorker(Worker worker) {
        this.worker = worker;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }
}
