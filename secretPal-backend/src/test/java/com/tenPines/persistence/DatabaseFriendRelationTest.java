package com.tenPines.persistence;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class DatabaseFriendRelationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private SecretPalSystem secretPalSystem;

    @Before
    public void setUp() {
        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");
    }

    @Test
    public void When_I_Add_A_friend_relation_To_An_Event_It_Should_Get_Stored() throws Exception {
        SecretPalEvent event = new SecretPalEvent();
        secretPalSystem.saveEvent(event);

        Worker worker = new WorkerBuilder().build();
        worker.changeParticipationIntention();
        secretPalSystem.saveWorker(worker);

        Worker otherWorker = new WorkerBuilder().build();
        otherWorker.changeParticipationIntention();
        secretPalSystem.saveWorker(otherWorker);

        FriendRelation relation = secretPalSystem.createRelationInEvent(event, worker, otherWorker);

        assertThat(event.getFriendRelations(), hasSize(1));
        assertThat(event.getFriendRelations(), hasItem(hasProperty("giftGiver", is(worker))));
        assertThat(event.getFriendRelations(), hasItem(hasProperty("giftReceiver", is(otherWorker))));
    }
    @Test
    public void When_I_Remove_A_Friend_Relation_From_An_Event_It_Should_Be_No_More() throws Exception {
        SecretPalEvent event = new SecretPalEvent();
        secretPalSystem.saveEvent(event);

        Worker worker = new WorkerBuilder().build();
        worker.changeParticipationIntention();
        secretPalSystem.saveWorker(worker);

        Worker otherWorker = new WorkerBuilder().build();
        otherWorker.changeParticipationIntention();
        secretPalSystem.saveWorker(otherWorker);

        FriendRelation relation = secretPalSystem.createRelationInEvent(event, worker, otherWorker);
        secretPalSystem.deleteRelationInEvent(relation);

        assertThat(secretPalSystem.retrieveEvent(event).getFriendRelations(), hasSize(0));
        assertThat(event.getFriendRelations(), not(hasItem(hasProperty("giftGiver", is(worker)))));
        assertThat(event.getFriendRelations(), not(hasItem(hasProperty("giftReceiver", is(otherWorker)))));
    }

}