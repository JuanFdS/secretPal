package model;

import builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SecretPalEventTest {

    private SecretPalEvent aSecretPalEvent;
    private Participant aParticipant;
    private Person aPerson;
    private Person otherPerson;

    @Before
    public void setUp() throws Exception {
        aSecretPalEvent = new SecretPalEvent(new ArrayList<>());
        aPerson = new PersonBuilder().build();
        aPerson.setWantsToParticipate(true);
        otherPerson = new PersonBuilder().build();
        otherPerson.setWantsToParticipate(true);
        aParticipant = new Participant(aPerson, otherPerson);
    }

    @Test
    public void When_I_create_a_new_secret_pal_event_it_has_no_participants() {
        eventHasNoParticipants();
    }

    @Test
    public void When_I_add_a_participant_to_a_secret_pal_event_it_has_participants(){
        aSecretPalEvent.registerParticipant(aParticipant);

        eventHasParticipants();
        amountOfParticipants(1);
    }

    @Test
    public void When_I_add_two_participants_the_amount_of_participants_is_two() throws Exception {
        Participant otherParticipant = new Participant(otherPerson, aPerson);

        aSecretPalEvent.registerParticipant(aParticipant);
        aSecretPalEvent.registerParticipant(otherParticipant);

        amountOfParticipants(2);
    }

    @Test
    public void When_I_add_two_times_the_same_person_an_exception_is_raised(){
    try {
        aSecretPalEvent.registerParticipant(aParticipant);
        aSecretPalEvent.registerParticipant(aParticipant);
        fail("Exception was not raised");
    } catch (RuntimeException e) {
        assertEquals(e.getMessage(), "That user was already registered in the event");
    }
        amountOfParticipants(1);
    }


    private void eventHasParticipants(){
        assertTrue(aSecretPalEvent.hasAnyParticipant());
    }

    private void eventHasNoParticipants(){
        assertFalse(aSecretPalEvent.hasAnyParticipant());
    }

    private void amountOfParticipants(int amount){
        assertEquals(aSecretPalEvent.amountOfParticipant(), amount);
    }
}
