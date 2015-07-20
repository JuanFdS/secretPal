package application;

import builder.PersonBuilder;
import model.Worker;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

public class secretPalSystemTest {

    private SecretPalSystem secretPalSystem;
    Worker aWorker;

    @Before
    public void setUp() {
        secretPalSystem = new SecretPalSystem();
        aWorker = new PersonBuilder().build();
    }

    @Test
    public void when_a_worker_wants_to_participate_then_his_intention_changes() {
        secretPalSystem.savePerson(aWorker);
        aWorker.changeParticipationIntention(true);

        secretPalSystem.changeIntention(aWorker);

        assertTrue(secretPalSystem.getWorker(aWorker).wantsToParticipate());
    }

    @Test
    public void when_a_worker_wants_to_participate_but_he_is_not_save_then_it_should_raise_an_exception() {
        try {
            secretPalSystem.changeIntention(aWorker);
            fail("An exception should raise!");
        }
        catch (RuntimeException e) {
            assertEquals(e.getMessage(), "No value present");
        }

        assertFalse(aWorker.wantsToParticipate());
    }
}
