package model;

public class Participant {

    private Person participant;
    private Person secretPal;

    public Participant(){}

    public Participant(Person participant, Person secretPal) throws Exception {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(secretPal);
        checkIfNotTheSame(participant, secretPal);
        this.participant = participant;
        this.secretPal = secretPal;
    }

    private void checkIfNotTheSame(Person participant, Person secretPal) throws Exception {
        if(participant.equals(secretPal))
            throw new RuntimeException("You cant assign the participant to be his secretPal");
    }
    private void checkIfWantToParticipate(Person person) throws Exception {
        if(!person.wantsToParticipate()) throw new RuntimeException(person.getFullName()  + " does not want to participate");
    }

    public Person getParticipant() {
        return this.participant;
    }

    public Person getSecretPal() {
        return this.secretPal;
    }
}
