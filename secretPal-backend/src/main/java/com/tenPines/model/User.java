package com.tenPines.model;


import com.tenPines.configuration.AdminProperties;

import java.io.IOException;

public class User {

    public Worker worker;

    public User(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public boolean isAdmin() throws IOException {
        return worker.geteMail().equals(AdminProperties.getAdminEmail());
    }
}