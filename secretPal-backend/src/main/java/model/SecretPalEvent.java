package model;


import java.util.List;

public class SecretPalEvent {

    private List<Participant> participants;

    public SecretPalEvent(List<Participant> participants){
        this.participants = participants;
    }

    public boolean hasAnyParticipant() {
        return !this.participants.isEmpty();
    }

    public void registerParticipant(Participant aParticipant) {
        /*TODO: mapear solo con particpantes y ver si participa, y mapear solo con secretPal y ver si ya esta asignado*/
        if (this.participants.contains(aParticipant)) {
            throw new RuntimeException("That user was already registered in the event");
        } else {
            this.participants.add(aParticipant);
        }
    }

    public int amountOfParticipant() {
        return participants.size();
    }
}
