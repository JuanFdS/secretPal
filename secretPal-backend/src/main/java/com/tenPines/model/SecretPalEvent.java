package com.tenPines.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    @ManyToMany(cascade = {CascadeType.ALL})
    private Set<Person> participants = new HashSet<>();

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Set<Person> getParticipants() {
        return participants;
    }

    public void registerParticipant(Person... participants) {
        Collections.addAll(this.participants, participants);
    }

    public boolean hasAnyParticipant() {
        return !this.participants.isEmpty();
    }

    public int amountOfParticipant() {
        return participants.size();
    }
}
