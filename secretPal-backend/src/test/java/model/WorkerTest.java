package model;

import builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;
import java.util.function.Supplier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class WorkerTest {

    private PersonBuilder personBuilder;

    @Before
    public void setUp(){
        this.personBuilder = new PersonBuilder();
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_name_an_exception_is_raised(){
        Supplier<Worker> aPersonWithNullName = () -> this.personBuilder.withFullName(null).build();
        checkExceptionIsRaisedUponCreation(aPersonWithNullName, "Full name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_name_an_exception_is_raised(){
        Supplier<Worker> aPersonWithAnEmptyName = () -> this.personBuilder.withFullName("").build();
        checkExceptionIsRaisedUponCreation(aPersonWithAnEmptyName, "Full name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_non_char_name_an_exception_is_raised(){
        Supplier<Worker> aPersonWithANonCharName = () -> this.personBuilder.withFullName("123$_").build();
        checkExceptionIsRaisedUponCreation(aPersonWithANonCharName, "Full name is invalid");
    }


    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_email_an_exception_is_raised(){
        Supplier<Worker> aPersonWithInvalidEmail = () -> personBuilder.withEmail("invalidEmail").build();
        checkExceptionIsRaisedUponCreation(aPersonWithInvalidEmail, "Email is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_last_name_that_contains_a_single_quote_on_it_should_not_raise_an_exception(){
        Worker aWorker = personBuilder.withFullName("Jason O'Connel").build();
        assertEquals(aWorker.getFullName(), "Jason O'Connel");
    }

    @Test
    public void When_I_try_to_create_a_person_he_should_not_want_to_participate_yet(){
        Worker aWorker = personBuilder.build();
        assertFalse(aWorker.getWantsToParticipate());
    }

    private void checkExceptionIsRaisedUponCreation(Supplier<Worker> creationFunction, String assertionMessage){
        try {
            creationFunction.get();
            fail("The exception was not raised");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), assertionMessage);
        }
    }
}
