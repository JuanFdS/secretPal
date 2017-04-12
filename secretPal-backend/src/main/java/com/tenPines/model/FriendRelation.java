package com.tenPines.model;

import com.tenPines.builder.FriendRelationMessageBuilder;

import javax.mail.MessagingException;
import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;

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

    @OneToOne
    private LocalDate creationDate;

    public FriendRelation(){}

    public FriendRelation(Worker participant, Worker aGiftReceiver)  {
        giftGiver = participant;
        giftReceiver = aGiftReceiver;
        creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Worker getGiftGiver() {
        return this.giftGiver;
    }

    public void setGiftGiver(Worker giftGiver) {
        this.giftGiver = giftGiver;
    }

    public void setCreationDate(LocalDate aDate) {
        this.creationDate = aDate;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public Worker getGiftReceiver() {
        return this.giftReceiver;
    }

    public void setGiftReceiver(Worker giftReceiver) {
        this.giftReceiver = giftReceiver;
    }
}
