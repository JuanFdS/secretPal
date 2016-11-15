package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.process.AssignmentException;
import com.tenPines.model.process.RelationEstablisher;
import org.junit.Before;
import org.junit.Test;

import static com.tenPines.model.process.AssignmentException.Reason.CANT_SELF_ASSIGN;
import static com.tenPines.model.process.AssignmentException.Reason.DOES_NOT_WANT_TO_PARTICIPATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class FriendRelationTest {

    private Worker aWorker;
    private Worker otherWorker;

    @Before
    public void setUp(){
        this.aWorker = new WorkerBuilder().build();
        this.otherWorker = new WorkerBuilder().build();
    }

    @Test
    public void When_I_try_to_create_a_participant_that_does_not_want_to_participate_an_exception_is_raised() {
        aWorker.setWantsToParticipate(false);
        RelationEstablisher relationEstablisher = new RelationEstablisher(aWorker, otherWorker);

        try {
            relationEstablisher.createRelation();
            fail("The exception was not raised");
        } catch (AssignmentException e) {
            assertThat(e.getReason(), is(DOES_NOT_WANT_TO_PARTICIPATE));
            assertThat(e.getDetails(), hasEntry("worker",aWorker));
        }
    }


    @Test  //
    public void When_I_try_to_create_a_participant_whose_secretpal_is_him_an_exception_is_raised() {
        RelationEstablisher relationEstablisher = new RelationEstablisher(aWorker, aWorker);

        try {
            relationEstablisher.createRelation();
            fail("The exception was not raised");
        } catch (AssignmentException e) {
            assertThat(e.getReason(), is(CANT_SELF_ASSIGN));
        }
    }


    @Test
    public void When_I_try_to_relate_two_willing_participants_all_is_ok() {
        RelationEstablisher relationEstablisher = new RelationEstablisher(aWorker, otherWorker);

        FriendRelation relation = relationEstablisher.createRelation();
        assertThat(relation, notNullValue());
    }

}
