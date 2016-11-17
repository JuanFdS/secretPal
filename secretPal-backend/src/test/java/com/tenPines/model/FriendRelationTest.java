package com.tenPines.model;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.integration.SpringBaseTest;
import com.tenPines.model.process.AssignmentException;
import com.tenPines.model.process.RelationEstablisher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.tenPines.model.process.AssignmentException.Reason.CANT_SELF_ASSIGN;
import static com.tenPines.model.process.AssignmentException.Reason.DOES_NOT_WANT_TO_PARTICIPATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FriendRelationTest extends SpringBaseTest{

    private Worker aWorker;
    private Worker otherWorker;

    @Autowired
    FriendRelationService friendRelationService;
    @Autowired
    WorkerService workerService;
    @Autowired
    UserService userService;

    private InitializerLocalSystem initializerLocalSystem;

    @Before
    public void setUp(){
        this.aWorker = new WorkerBuilder().build();
        this.otherWorker = new WorkerBuilder().build();
        initializerLocalSystem = new InitializerLocalSystem(workerService,userService);
    }

    @Test   //Cuando intento crear un participante que no quiere participar, se plantea una excepción
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


    @Test  //Cuando trato de crear un participante cuyo secretpal es él se plantea una excepción
    public void When_I_try_to_create_a_participant_whose_secretpal_is_him_an_exception_is_raised() {
        RelationEstablisher relationEstablisher = new RelationEstablisher(aWorker, aWorker);

        try {
            relationEstablisher.createRelation();
            fail("The exception was not raised");
        } catch (AssignmentException e) {
            assertThat(e.getReason(), is(CANT_SELF_ASSIGN));
        }
    }


    @Test  //Cuando trato de relacionar dos participantes dispuestos tod0 está bien
    public void When_I_try_to_relate_two_willing_participants_all_is_ok() {
        RelationEstablisher relationEstablisher = new RelationEstablisher(aWorker, otherWorker);

        FriendRelation relation = relationEstablisher.createRelation();
        assertThat(relation, notNullValue());
    }

    @Test  //La primera vez que inicio el juego, tengo una lista de pinos precargada y debo crear relaciones entre todos de forma circular
    public void test1() {
        initializerLocalSystem.init();

        friendRelationService.autoAssignRelations();
        List<FriendRelation> relations = friendRelationService.getAllRelations();

        assertTrue(relations.size() == 5);
    }



}
