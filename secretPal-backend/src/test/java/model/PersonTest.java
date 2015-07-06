package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonTest {

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_name_an_exception_is_raised(){
        try {
            new Person(null, "Pepe", LocalDate.of(1992, Month.APRIL,12));
            fail("The exception was not raised");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Name is invalid");
        }
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_name_an_exception_is_raised(){
        try {
            new Person("", "Pepe", LocalDate.of(1992, Month.APRIL,12));
            fail("The exception was not raised");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Name is invalid");
        }
    }
}
