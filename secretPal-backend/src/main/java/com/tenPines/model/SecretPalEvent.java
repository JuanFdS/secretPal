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



    /* TODO Estaria bueno diferenciarlos. No se si asi
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    private LocalDate startingDate; */
    /* TODO Hacer la DB para los participantes @ManyToMany(cascade = {CascadeType.ALL}) */
    @OneToMany(cascade = {CascadeType.ALL})
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

    public void registerParticipant(FriendRelation aFriendRelation) {
        List<Worker> participantsToCheck = friendRelations.stream().map(p -> p.getParticipant()).collect(Collectors.toList());

        if (participantsToCheck.contains(aFriendRelation.getParticipant()) ) {
            throw new RuntimeException("That user was already registered in the event");
        } else {
            List<Worker> secretPalsToCheck = friendRelations.stream().map(p -> p.getSecretPal()).collect(Collectors.toList());

            if (secretPalsToCheck.contains(aFriendRelation.getSecretPal())) {
                throw new RuntimeException("The secretPal was already assign to other participant");
            } else {
                this.friendRelations.add(aFriendRelation);
            }
        }
    }

    public boolean hasAnyParticipant() {
        return !this.friendRelations.isEmpty();
    }

    public int amountOfParticipant() {
        return friendRelations.size();
    }
}
