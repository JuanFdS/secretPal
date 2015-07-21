package com.tenPines.model;

public class Wish {
    public Worker worker;
    public String wish;

    public Wish(Worker worker, String wish) {
        this.worker = worker;
        this.wish = wish;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }
}
