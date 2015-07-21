package com.tenPines.restAPI;


import com.tenPines.model.Worker;

public class WorkerWithRelation {

    private Worker giftGiver;
    private Worker giftReceiver;

    public WorkerWithRelation(Worker giftGiver, Worker giftReceiver){
        this.giftGiver = giftGiver;
        this.giftReceiver = giftReceiver;
    }

    public Worker getGiftGiver() {
        return giftGiver;
    }

    public void setGiftGiver(Worker giftGiver) {
        this.giftGiver = giftGiver;
    }

    public Worker getGiftReceiver() {
        return giftReceiver;
    }

    public void setGiftReceiver(Worker giftReceiver) {
        this.giftReceiver = giftReceiver;
    }
}
