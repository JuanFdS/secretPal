package application;

import builder.PersonBuilder;
import model.Worker;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

public class secretPalSystemTest {

    @Test
    public void when_a_worker_wants_to_participate_then_his_intention_changes() {
        SecretPalSystem secretPalSystem = new SecretPalSystem();
        Worker aWorker = new PersonBuilder().build();
        secretPalSystem.savePerson(aWorker);

        secretPalSystem.changeIntention(aWorker, true);

        assertTrue(aWorker.wantsToParticipate());
    }

    @Test
    public void when_a_worker_wants_to_participate_but_he_is_not_save_then_it_should_raise_an_exception() {
        SecretPalSystem secretPalSystem = new SecretPalSystem();
        Worker aWorker = new PersonBuilder().build();

        try {
            secretPalSystem.changeIntention(aWorker, true);
            fail("An exception should raise!");
        }
        catch (RuntimeException e) {
            assertEquals(e.getMessage(), "No value present");
        }

        assertFalse(aWorker.wantsToParticipate());
    }
}
