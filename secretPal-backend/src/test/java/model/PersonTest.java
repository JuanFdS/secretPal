package model;

import builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.Function;
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
        checkExceptionIsRaisedUponCreation(() -> this.personBuilder.withName(null).build() , "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_name_an_exception_is_raised(){
        checkExceptionIsRaisedUponCreation(() -> this.personBuilder.withName("").build(), "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_non_char_name_an_exception_is_raised(){
        checkExceptionIsRaisedUponCreation(() -> this.personBuilder.withName("123$_").build(), "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_last_name_an_exception_is_raised(){
        checkExceptionIsRaisedUponCreation(() -> this.personBuilder.withLastName(null).build(), "Last name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_last_name_an_exception_is_raised(){
        checkExceptionIsRaisedUponCreation(() -> this.personBuilder.withLastName("").build(), "Last name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_non_char_last_name_an_exception_is_raised(){
        checkExceptionIsRaisedUponCreation(() -> this.personBuilder.withLastName("123$_").build(), "Last name is invalid");
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
