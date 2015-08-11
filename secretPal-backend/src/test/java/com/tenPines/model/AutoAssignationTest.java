package com.tenPines.model;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class AutoAssignationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private SecretPalSystem secretPalSystem;

    @Before
    public void setUp() {
        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");
    }

    @Test
    public void When_there_is_no_participants_then_it_should_raise_an_exception(){
        SecretPalEvent event = secretPalSystem.retrieveCurrentEvent();
        List<Worker> participants = secretPalSystem.retrieveParticipants();
        try {
            secretPalSystem.autoAssignRelationsFor(event, participants);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't assign with less than 2 people");
            assertThat(event.getFriendRelations(), hasSize(0));
        }
    }

    @Test
    public void When_there_is_2_participants_then_when_assign_each_participant_is_the_other_one_secret_pal() throws Exception {
        SecretPalEvent event = secretPalSystem.retrieveCurrentEvent();

        Worker worker = new WorkerBuilder().build();
        worker.changeParticipationIntention();
        secretPalSystem.saveWorker(worker);

        Worker otherWorker = new WorkerBuilder().build();
        otherWorker.changeParticipationIntention();
        secretPalSystem.saveWorker(otherWorker);

        List<Worker> participants = secretPalSystem.retrieveParticipants();
        secretPalSystem.autoAssignRelationsFor(event, participants);

        assertThat(event.getFriendRelations(), hasSize(2));
        assertThat(event.getFriendRelations(), hasItem(hasProperty("giftGiver", is(worker))));  //se debe asertar que este mismo item tiene la property "giftReceiver", is(otherWorker)
        assertThat(event.getFriendRelations(), hasItem(hasProperty("giftGiver", is(otherWorker))));
    }

}