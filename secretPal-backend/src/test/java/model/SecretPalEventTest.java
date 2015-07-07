package model;

import builder.PersonBuilder;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class SecretPalEventTest {

    @Test
    public void When_I_create_a_new_secret_pal_event_it_has_no_participants() {
        SecretPalEvent aSecretPalEvent = new SecretPalEvent(new ArrayList<>());
        assertFalse(aSecretPalEvent.hasAnyParticipant());
    }

    @Test
    public void When_I_add_a_participant_to_a_secret_pal_event_it_has_participants(){
        SecretPalEvent aSecretPalEvent = new SecretPalEvent(new ArrayList<>());
        Person aRandomPerson = new PersonBuilder().build();
        aSecretPalEvent.registerParticipant(aRandomPerson);

        assertTrue(aSecretPalEvent.hasAnyParticipant());
    }
}
