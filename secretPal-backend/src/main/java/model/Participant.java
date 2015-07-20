package model;

public class Participant {

    private Person participant;
    private Person secretPal;

    public Participant(){}

    public Participant(Person participant, Person secretPal)  {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(secretPal);
        checkIfNotTheSameParticipant(participant, secretPal);
        this.participant = participant;
        this.secretPal = secretPal;
    }

    private void checkIfNotTheSameParticipant(Person participant, Person secretPal) {
        if(participant.equals(secretPal))
            throw new RuntimeException("You cant assign the participant to be his secretPal");
    }
    private void checkIfWantToParticipate(Person person) {
        if(!person.wantsToParticipate()) throw new RuntimeException(person.fullName()  + " does not want to participate");
    }


    public Person getParticipant() {
        return this.participant;
    }

    public Person getSecretPal() {
        return this.secretPal;
    }
}
