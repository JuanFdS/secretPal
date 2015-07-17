package model;

import builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;
import java.util.function.Supplier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonTest {

    private PersonBuilder personBuilder;

    @Before
    public void setUp(){
        this.personBuilder = new PersonBuilder();
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_name_an_exception_is_raised(){
        Supplier<Person> aPersonWithNullName = () -> this.personBuilder.withName(null).build();
        checkExceptionIsRaisedUponCreation(aPersonWithNullName, "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_name_an_exception_is_raised(){
        Supplier<Person> aPersonWithAnEmptyName = () -> this.personBuilder.withName("").build();
        checkExceptionIsRaisedUponCreation(aPersonWithAnEmptyName, "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_non_char_name_an_exception_is_raised(){
        Supplier<Person> aPersonWithANonCharName = () -> this.personBuilder.withName("123$_'.").build();
        checkExceptionIsRaisedUponCreation(aPersonWithANonCharName, "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_email_an_exception_is_raised(){
        Supplier<Person> aPersonWithInvalidEmail = () -> personBuilder.withEmail("invalidEmail").build();
        checkExceptionIsRaisedUponCreation(aPersonWithInvalidEmail, "Email is invalid");
    }

    private void checkExceptionIsRaisedUponCreation(Supplier<Person> creationFunction, String assertionMessage){
        try {
            creationFunction.get();
            fail("The exception was not raised");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), assertionMessage);
        }
    }
}
