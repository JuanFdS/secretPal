package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class SecretPalEvent {

    @Id
    @GeneratedValue
    private Long id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    private LocalDate startingDate;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public void setParticipants(Set<Person> participants) {
        this.participants = participants;
    }

    public Set<Person> getParticipants() {
        return participants;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    private Set<Person> participants = new HashSet<>();

    public void registerParticipant(Person... participants) {
        for (Person person : participants) {
            this.participants.add(person);
        }
    }

    public boolean hasAnyParticipant() {
        return !this.participants.isEmpty();
    }

    public int amountOfParticipant() {
        return participants.size();
    }
}
