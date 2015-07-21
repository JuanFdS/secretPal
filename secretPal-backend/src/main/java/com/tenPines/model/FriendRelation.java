package com.tenPines.model;


import javax.persistence.*;

@Entity
@Table
public class FriendRelation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private Worker giftGiver;

    @OneToOne(cascade = {CascadeType.ALL})
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


    public void setGiftGiver(Worker giftGiver) {
        this.giftGiver = giftGiver;
    }

    public void setGiftReceiver(Worker giftReceiver) {
        this.giftReceiver = giftReceiver;
    }

    public Worker getGiftGiver() {
        return this.giftGiver;
    }

    public Worker getGiftReceiver() {
        return this.giftReceiver;
    }
}
