package model;


import java.util.List;

public class SecretPalEvent {

    private List<Person> participants;

    public SecretPalEvent(List<Person> participants){
        this.participants = participants;
    }

    public boolean hasAnyParticipant() {
        return !this.participants.isEmpty();
    }

    public void registerParticipant(Person aPerson) {
        this.participants.add(aPerson);
    }
}
