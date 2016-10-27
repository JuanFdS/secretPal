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

//    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
//    public SecretPalEvent event;

    public FriendRelation(){}

    public FriendRelation(Worker participant, Worker giftReceiver)  {
        this.giftGiver = participant;
        this.giftReceiver = giftReceiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public SecretPalEvent getEvent() {
//        return event;
//    }
//
//    public void setEvent(SecretPalEvent event) {
//        this.event = event;
//    }

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
