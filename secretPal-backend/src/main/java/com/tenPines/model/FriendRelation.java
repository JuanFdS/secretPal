package com.tenPines.model;

import com.tenPines.model.Worker;

public class FriendRelation {

    private Worker participant;
    private Worker secretPal;

    public FriendRelation(){}

    public FriendRelation(Worker participant, Worker secretPal)  {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(secretPal);
        checkIfNotTheSameParticipant(participant, secretPal);
        this.participant = participant;
        this.secretPal = secretPal;
    }

    private void checkIfNotTheSameParticipant(Worker participant, Worker secretPal) {
        if(participant.equals(secretPal))
            throw new RuntimeException("You cant assign the participant to be his secretPal");
    }
    private void checkIfWantToParticipate(Worker worker) {
        if(!worker.getWantsToParticipate()) throw new RuntimeException(worker.getFullName()  + " does not want to participate");
    }


    public Worker getParticipant() {
        return this.participant;
    }

    public Worker getSecretPal() {
        return this.secretPal;
    }
}
