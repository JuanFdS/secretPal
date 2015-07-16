package model;

public class Participant {

    private Person participant;
    private Person secretPal;

    public Participant(){}

    public Participant(Person participant, Person secretPal) throws Exception {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(secretPal);
        this.participant = participant;
        this.secretPal = secretPal;
    }

    private void checkIfWantToParticipate(Person person) throws Exception {
        if(!person.wantsToParticipate()) throw new RuntimeException(person.getName() + " " + person.getLastName()  + " does not want to participate");
    }
}
