package model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonTest {

    @Test
    public void When_I_try_to_create_a_person_with_an_invalid_name_an_exception_is_raised(){
        checkException(() -> new Person(null, "Pepe", LocalDate.of(1992, Month.APRIL,12)), "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_an_empty_name_an_exception_is_raised(){
        checkException(() -> new Person("", "Pepe", LocalDate.of(1992, Month.APRIL,12)), "Name is invalid");
    }

    @Test
    public void When_I_try_to_create_a_person_with_a_non_char_name_an_exception_is_raised(){
        checkException(() -> new Person("123$_", "Pepe", LocalDate.of(1992, Month.APRIL,12)), "Name is invalid");
    }

    private void checkException(Supplier<Person> creationFunction, String assertionMessage){
        try {
            creationFunction.get();
            fail("The exception was not raised");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), assertionMessage);
        }
    }
}
