package com.tenPines.model;


import javax.persistence.*;

@Entity
@Table
public class FriendRelation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private Worker participant;

    @OneToOne(cascade = {CascadeType.ALL})
    private Worker secretPal;

    public FriendRelation(){}

    public FriendRelation(Worker participant, Worker secretPal)  {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(secretPal);
        checkIfNotTheSameParticipant(participant, secretPal);
        this.participant = participant;
        this.secretPal = secretPal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    private void checkIfNotTheSameParticipant(Worker participant, Worker secretPal) {
        if(participant.equals(secretPal))
            throw new RuntimeException("You cant assign the participant to be his secretPal");
    }
    private void checkIfWantToParticipate(Worker worker) {
        if(!worker.getWantsToParticipate()) throw new RuntimeException(worker.getFullName()  + " does not want to participate");
    }


    public void setParticipant(Worker participant) {
        this.participant = participant;
    }

    public void setSecretPal(Worker secretPal) {
        this.secretPal = secretPal;
    }

    public Worker getParticipant() {
        return this.participant;
    }

    public Worker getSecretPal() {
        return this.secretPal;
    }
}
