package model;

import builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SecretPalEventTest {

    private SecretPalEvent aSecretPalEvent;

    @Before
    public void setUp(){
        aSecretPalEvent = new SecretPalEvent();
    }

    @Test
    public void When_I_create_a_new_secret_pal_event_it_has_no_participants() {
        eventHasNoParticipants();
    }

    @Test
    public void When_I_add_a_participant_to_a_secret_pal_event_it_has_participants(){
        Person aRandomPerson = new PersonBuilder().build();
        aSecretPalEvent.registerParticipant(aRandomPerson);

        eventHasParticipants();
    }

    @Test
    public void When_I_add_two_participants_the_amount_of_participants_is_two(){
        Person aRandomPerson = new PersonBuilder().build();
        Person anotherRandomPerson = new PersonBuilder().build();

        aSecretPalEvent.registerParticipant(aRandomPerson);
        aSecretPalEvent.registerParticipant(anotherRandomPerson);

        amountOfParticipants(2);
    }

    @Test
    public void When_I_add_two_participants_as_an_array_the_amount_of_participants_is_two(){
        Person aRandomPerson = new PersonBuilder().build();
        Person anotherRandomPerson = new PersonBuilder().build();

        aSecretPalEvent.registerParticipant(aRandomPerson, anotherRandomPerson);

        amountOfParticipants(2);
    }

    @Test
    public void When_I_add_one_participants_the_amount_of_participants_is_one(){
        Person aRandomPerson = new PersonBuilder().build();

        aSecretPalEvent.registerParticipant(aRandomPerson);

        amountOfParticipants(1);
    }

    @Test
    public void When_I_add_two_times_the_same_person_only_one_stays(){
        Person aRandomPerson = new PersonBuilder().build();
        aSecretPalEvent.registerParticipant(aRandomPerson);
        aSecretPalEvent.registerParticipant(aRandomPerson);

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
