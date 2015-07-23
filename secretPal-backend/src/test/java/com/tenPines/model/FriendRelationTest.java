package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FriendRelationTest {

    private WorkerBuilder workerBuilder;
    private Worker aWorker;
    private Worker otherWorker;
    private FriendRelation friendRelation;

    @Before
    public void setUp() throws Exception {
        this.aWorker = new WorkerBuilder().withFullName("Victoria Cabrera").build();
        this.otherWorker = new WorkerBuilder().withFullName("Maria Cabrera").build();
    }

    @Test
    public void When_I_try_to_create_a_participant_that_does_not_want_to_participate_an_exception_is_raised(){
        otherWorker.changeParticipationIntention();

        try {
            friendRelation = new FriendRelation(aWorker, otherWorker);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Victoria Cabrera does not want to participate");
        }
    }

    @Test
    public void When_I_try_to_create_a_participant_whose_secretpal_does_not_want_to_participate_an_exception_is_raised(){
        aWorker.changeParticipationIntention();

        try {
            friendRelation = new FriendRelation(aWorker, otherWorker);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Maria Cabrera does not want to participate");
        }
    }

    @Test
    public void When_I_try_to_create_a_participant_whose_secretpal_is_him_an_exception_is_raised(){
        aWorker.changeParticipationIntention();

        try {
            friendRelation = new FriendRelation(aWorker, aWorker);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "You cant assign the giftGiver to be his giftReceiver");
        }
    }

}
