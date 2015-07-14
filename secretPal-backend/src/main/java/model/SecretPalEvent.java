package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

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
