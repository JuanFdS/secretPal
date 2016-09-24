package com.tenPines.model.process;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;

import static com.tenPines.model.process.AssignmentException.Reason.CANT_SELF_ASSIGN;

public class RelationEstablisher {
    private final Worker giftgiver;
    private final Worker giftReciever;

    public RelationEstablisher(Worker giftgiver, Worker giftReciever) {
        this.giftgiver = giftgiver;
        this.giftReciever = giftReciever;
    }

    public FriendRelation createRelation() {
        checkIfWantToParticipate(giftgiver);
        checkIfWantToParticipate(giftReciever);
        checkIfNotTheSameParticipant(giftgiver, giftReciever);

        return relate();
    }

    private FriendRelation relate() {
        return new FriendRelation(giftgiver, giftReciever);
    }

    private void checkIfNotTheSameParticipant(Worker participant, Worker secretPal) {
        if(participant.equals(secretPal))
            throw new AssignmentException(CANT_SELF_ASSIGN);
    }
    private void checkIfWantToParticipate(Worker worker) {
        if(!worker.getWantsToParticipate())
            throw new AssignmentException(AssignmentException.Reason.DOES_NOT_WANT_TO_PARTICIPATE, worker);
    }
}
