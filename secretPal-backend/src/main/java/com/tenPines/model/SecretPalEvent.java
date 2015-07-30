package com.tenPines.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class SecretPalEvent {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch=FetchType.EAGER)
    private List<FriendRelation> friendRelations = new ArrayList<>();

    public SecretPalEvent() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public List<FriendRelation> getFriendRelations() {
        return friendRelations;
    }

    public void setFriendRelations(List<FriendRelation> friendRelations) {
        this.friendRelations = friendRelations;
    }

    public void registerParticipant(FriendRelation aFriendRelation) {
        List<Worker> participantsToCheck = this.getFriendRelations().stream().map(FriendRelation::getGiftGiver).collect(Collectors.toList());

        if (participantsToCheck.contains(aFriendRelation.getGiftGiver()) ) {
            throw new RuntimeException("That user was already registered in the event");
        } else {
            List<Worker> secretPalsToCheck = this.getFriendRelations().stream().map(FriendRelation::getGiftReceiver).collect(Collectors.toList());

            if (secretPalsToCheck.contains(aFriendRelation.getGiftReceiver())) {
                throw new RuntimeException("The secretPal was already assign to other participant");
            } else {
                this.getFriendRelations().add(aFriendRelation);
            }
        }
    }

    public boolean hasAnyParticipant() {
        return !this.friendRelations.isEmpty();
    }

    public int amountOfParticipant() {
        return friendRelations.size();
    }

    public void deleteRelation(FriendRelation friendRelation) {
        this.getFriendRelations().remove(friendRelation);
    }
}
