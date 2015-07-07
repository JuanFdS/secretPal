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
        if (this.participants.contains(aPerson)) {
            throw new RuntimeException("That user was already registered in the event");
        } else {
            this.participants.add(aPerson);
        }
    }

    public int amountOfParticipant() {
        return participants.size();
    }
}
