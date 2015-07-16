package model;


import java.util.List;
import java.util.stream.Collectors;

public class SecretPalEvent {

    private List<Participant> participants;

    public SecretPalEvent(List<Participant> participants){
        this.participants = participants;
    }

    public boolean hasAnyParticipant() {
        return !this.participants.isEmpty();
    }

    public void registerParticipant(Participant aParticipant) {
        List<Person> participantsToCheck = participants.stream().map(p -> p.getParticipant()).collect(Collectors.toList());

        if (participantsToCheck.contains(aParticipant.getParticipant()) ) {
            throw new RuntimeException("That user was already registered in the event");
        } else {
            List<Person> secretPalsToCheck = participants.stream().map(p -> p.getSecretPal()).collect(Collectors.toList());

            if (secretPalsToCheck.contains(aParticipant.getSecretPal())) {
                throw new RuntimeException("The secretPal was already assign to other participant");
            } else {
                this.participants.add(aParticipant);
            }
        }
    }
    public int amountOfParticipant() {
        return participants.size();
    }
}
