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

    @Column
    private LocalDate scheduledDate;

    @Column
    private Boolean wasFulfilled;

    private FriendRelation(){}

    public FriendRelation(Worker giftGiver, Worker giftReceiver, int currentYear)  {
        ensureGiverAndReceiverAreNotTheSame(giftGiver, giftReceiver);
        this.giftGiver = giftGiver;
        this.giftReceiver = giftReceiver;
        this.scheduledDate = this.giftReceiver.getBirthday().withYear(currentYear);
        this.wasFulfilled = false;
    }

    private void ensureGiverAndReceiverAreNotTheSame(Worker giftGiver, Worker giftReceiver) {
        if(giftGiver.equals(giftReceiver))
            throw new RuntimeException("Nadie se puede regalar a si mismo");
    }

    public Worker getGiftGiver() {
        return giftGiver;
    }

    public Worker getGiftReceiver() {
        return giftReceiver;
    }

    public Boolean getWasFulfilled() {
        return wasFulfilled;
    }
}
