package model;

import builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ParticipantTest {

    private PersonBuilder personBuilder;
    private Person aPerson;
    private Person otherPerson;
    private Participant participant;

    @Before
    public void setUp(){
        this.personBuilder = new PersonBuilder();
        this.aPerson = personBuilder.withFullName("Victoria Cabrera").build();
        this.otherPerson = personBuilder.withFullName("Maria Cabrera").build();
    }

    @Test
    public void When_I_try_to_create_a_participant_that_does_not_want_to_participate_an_exception_is_raised(){
        otherPerson.setWantsToParticipate(true);

        try {
            participant = new Participant(aPerson, otherPerson);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Victoria Cabrera does not want to participate");
        }
    }

    @Test
    public void When_I_try_to_create_a_participant_whose_secretpal_does_not_want_to_participate_an_exception_is_raised(){
        aPerson.setWantsToParticipate(true);

        try {
            participant = new Participant(aPerson, otherPerson);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Maria Cabrera does not want to participate");
        }
    }

    @Test
    public void When_I_try_to_create_a_participant_whose_secretpal_is_him_an_exception_is_raised(){
        aPerson.setWantsToParticipate(true);

        try {
            participant = new Participant(aPerson, aPerson);
            fail("The exception was not raised");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "You cant assign the participant to be his secretPal");
        }
    }

}
