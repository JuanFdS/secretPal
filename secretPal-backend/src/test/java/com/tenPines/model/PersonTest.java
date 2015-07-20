package com.tenPines.model;

import com.tenPines.builder.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PersonTest {

    private PersonBuilder personBuilder;

    @Before
    public void setUp() {
        this.personBuilder = new PersonBuilder();
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_name_an_exception_is_raised() {
        Supplier<Person> aPersonWithNullName = () -> this.personBuilder.withFullName(null).build();
        checkExceptionIsRaisedUponCreation(aPersonWithNullName, "Full name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_name_an_exception_is_raised() {
        Supplier<Person> aPersonWithAnEmptyName = () -> this.personBuilder.withFullName("").build();
        checkExceptionIsRaisedUponCreation(aPersonWithAnEmptyName, "Full name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_non_char_name_an_exception_is_raised() {
        Supplier<Person> aPersonWithANonCharName = () -> this.personBuilder.withFullName("123$_").build();
        checkExceptionIsRaisedUponCreation(aPersonWithANonCharName, "Full name is invalid");
    }


    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_email_an_exception_is_raised() {
        Supplier<Person> aPersonWithInvalidEmail = () -> personBuilder.withEmail("invalidEmail").build();
        checkExceptionIsRaisedUponCreation(aPersonWithInvalidEmail, "Email is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_last_name_that_contains_a_single_quote_on_it_should_not_raise_an_exception() {
        Person aPerson = personBuilder.withFullName("Jason O'Connel").build();
        assertEquals(aPerson.getFullName(), "Jason O'Connel");
    }

    private void checkExceptionIsRaisedUponCreation(Supplier<Person> creationFunction, String assertionMessage) {
        try {
            creationFunction.get();
            fail("The exception was not raised");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is(assertionMessage));
        }
    }
}
