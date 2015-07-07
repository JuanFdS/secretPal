package model;

import org.junit.Test;
import static org.junit.Assert.*;


public class SecretPalEventTest {

    @Test
    public void When_I_create_a_new_secret_pal_event_it_has_no_participants() {
        SecretPalEvent aSecretPalEvent = new SecretPalEvent();
        assertFalse(aSecretPalEvent.hasAnyParticipant());
    }

}
