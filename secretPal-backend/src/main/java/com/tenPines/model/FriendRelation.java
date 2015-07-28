package com.tenPines.model;

import com.tenPines.builder.FriendRelationMessageBuilder;

import javax.mail.MessagingException;
import javax.persistence.*;
import java.io.IOException;

@Entity
@Table
public class FriendRelation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Worker giftGiver;

    @OneToOne
    private Worker giftReceiver;

    public FriendRelation(){}

    public FriendRelation(Worker participant, Worker giftReceiver)  {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(giftReceiver);
        checkIfNotTheSameParticipant(participant, giftReceiver);
        this.giftGiver = participant;
        this.giftReceiver = giftReceiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    private void checkIfNotTheSameParticipant(Worker participant, Worker secretPal) {
        if(participant.equals(secretPal))
            throw new RuntimeException("You cant assign the giftGiver to be his giftReceiver");
    }
    private void checkIfWantToParticipate(Worker worker) {
        if(!worker.getWantsToParticipate()) throw new RuntimeException(worker.getFullName()  + " does not want to participate");
    }

    public Worker getGiftGiver() {
        return this.giftGiver;
    }

    public void setGiftGiver(Worker giftGiver) {
        this.giftGiver = giftGiver;
    }

    public Worker getGiftReceiver() {
        return this.giftReceiver;
    }

    public void setGiftReceiver(Worker giftReceiver) {
        this.giftReceiver = giftReceiver;
    }

    public Message createMessage() throws IOException, MessagingException {
        return new FriendRelationMessageBuilder().buildMessage(this);
    }
}
