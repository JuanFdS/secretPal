package model;

public class Participant {

    private Worker participant;
    private Worker secretPal;

    public Participant(){}

    public Participant(Worker participant, Worker secretPal)  {
        checkIfWantToParticipate(participant);
        checkIfWantToParticipate(secretPal);
        checkIfNotTheSameParticipant(participant, secretPal);
        this.participant = participant;
        this.secretPal = secretPal;
    }

    private void checkIfNotTheSameParticipant(Worker participant, Worker secretPal) {
        if(participant.equals(secretPal))
            throw new RuntimeException("You cant assign the participant to be his secretPal");
    }
    private void checkIfWantToParticipate(Worker worker) {
        if(!worker.wantsToParticipate()) throw new RuntimeException(worker.fullName()  + " does not want to participate");
    }


    public Worker getParticipant() {
        return this.participant;
    }

    public Worker getSecretPal() {
        return this.secretPal;
    }
}
